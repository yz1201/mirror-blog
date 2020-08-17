// webpack.config.js
let path = require('path')

module.exports = {
  entry: './index.js',  //输入文件
  output: {
		path: path.join(__dirname, 'dist'),
		publicPath: '/dist/',  //输出路径
        filename: 'index.js', //输出文件
    },
  "scripts": {
        "test": "echo \"Error: no test specified\" && exit 1",
        "dev": "webpack-dev-server --open --host 0.0.0.0 --port 8888 --config webpack.config.js"
      }
}



