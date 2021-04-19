import Vue from 'vue'
import Router from 'vue-router'
import Dashboard from './views/Dashboard/Dashboard.vue'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'dashboard',
      component: Dashboard
    },
    {
      path: '/dailyPlan',
      name: 'dailyPlan',
      component: () => import('./views/DailyPlan/DailyPlan.vue')
    },
    {
      path: '/dailyReport',
      name: 'dailyReport',
      component: () => import('./views/DailyReport/DailyReport.vue')
    },
    {
      path: '/dataChart',
      name: 'dataChart',
      component: () => import('./views/DataChart/DataChart.vue')
    },
    {
      path: '/kpi',
      name: 'KPI',
      component: () => import('./views/KPI/KPI.vue')
    },
    {
      path: '/history',
      name: 'history',
      component: () => import('./views/History/History.vue')
    },
    {
      path: '/statistics',
      name: 'statistics',
      component: () => import('./views/Statistics/Statistics.vue')
    },
    {
      path: '/queue',
      name: 'queue',
      component: () => import('./views/WLM/Queue.vue')
    },
    {
      path: '/bots',
      name: 'bot',
      component: () => import('./views/Bot/Bot.vue')
    },
    {
      path: '/jobs',
      name: 'job',
      component: () => import('./views/Job/Job.vue')
    },
    {
      path: '/steps',
      name: 'step',
      component: () => import('./views/Step/Step.vue')
    },
    {
      path: '/codes',
      name: 'code',
      component: () => import('./views/Code/Code.vue')
    },
    {
      path: '/historyManagement',
      name: 'historyManagement',
      component: () => import('./views/HistoryManagement/HistoryManagement.vue')
    }
  ]
})
