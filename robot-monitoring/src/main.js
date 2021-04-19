import Vue from 'vue'
import App from './App.vue'
import router from './router'

import vuetify from './plugins/vuetify';
import 'vuetify/dist/vuetify.min.css'
import 'vue-material-design-icons/styles.css';
import 'typeface-roboto'
import store from './store'

import axios from 'axios'
import VueAxios from 'vue-axios'
import moment from "moment"
import VueMoment from 'vue-moment'
import VueDOMPurifyHTML from 'vue-dompurify-html'
import './plugins/base'
import 'chart.js'

Vue.config.productionTip = false
Vue.use(VueAxios, axios)
Vue.use(VueMoment, moment)
// Vue.use(window.VueCharts)
Vue.use(VueDOMPurifyHTML)

new Vue({
  vuetify,
  router,
  store,
  render: h => h(App)
}).$mount('#app')
