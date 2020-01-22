# 前端打包工具webpack总结

## 基本概念

### entry入口

### output出口

### loader加载器

## 安装

如果你使用 webpack 4+ 版本，你还需要安装 CLI

全局安装:``npm install -g webpack webpack-cli``

项目安装:``npm install -save-dev webpack webpack-cli``

## 配置

### 基本配置

新建配置文件``webpack.config.js``

```js
const path = require("path");

module.exports = {
    //入口文件
    entry: "./src/index.js",
    output: {
        //输入文件名称
        filename: "main.js",
        //输入文件夹
        path: path.resolve(__dirname, "dist")
    },
    module: {
        //loader 规则
        rules: [
            {
                test: /\.css$/,
                //先加载css-loader,再加载style-loader
                use: ["style-loader","css-loader"]
            }
        ]
    }
}
```

### node中配置

``package.json``的``scripts``中添加:

```json
"scripts": {
    "webpack": "webpack --config webpack.config.js"
}
```

打包:

```bash
npm run webpack
```

### 入口配置

### 出口配置

###　loader配置

### 插件配置

#### DevServer使用

#### HtmlWebpackPlugin

[GitHub文档](https://github.com/jantimon/html-webpack-plugin#configuration)

在出口文件夹中自动生成``html``文件,并引入打包之后的``js``.

webpack配置文件中引入插件模块:

```js
const HtmlWebpackPlugin = require('html-webpack-plugin');
```

创建该插件对象:

```js
plugins: [
        new HtmlWebpackPlugin({
            title: "ToDo"
        })
    ]
```