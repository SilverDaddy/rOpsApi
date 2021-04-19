module.exports = {
    outputDir: "../src/main/resources/static",
    indexPath: "../templates/index.html",
    /*devServer: {
      proxy: "http://localhost"
    },*/
  "transpileDependencies": [
    "vuetify"
  ],
  devServer: {
    overlay: false
  }
}