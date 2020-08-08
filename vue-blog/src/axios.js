import axios from 'axios'
import Element from 'element-ui'
import router from './router'
import store from './store'


axios.defaults.baseURL = "http://localhost:40923"

// axios.defaults.withCredentials = true

// 前置拦截
axios.interceptors.request.use(config => {
  return config
})

axios.interceptors.response.use(response => {
    let res = response.data;

    console.log("=================")
    console.log(res.statusCode)
    console.log("=================")

    if (res.statusCode === 200 ) {
      return response
    } else {
      Element.Message.error('错了哦，这是一条错误消息', {duration: 3 * 1000})

      return Promise.reject(response.data.msg)
    }
  },
  error => {
    console.log(error)
    if(error.response.data) {
      error.message = error.response.data.msg
    }

    if(error.response.status === 401) {
      // console.log("401 data"+$.store.get)
      store.commit("REMOVE_INFO")
      router.push("/login")
    }

    if (error.response.status === 403) {
      error.message = '权限不足，无法访问';
    }

    Element.Message.error(error.message, {duration: 3 * 1000})
    return Promise.reject(error)
  }
)