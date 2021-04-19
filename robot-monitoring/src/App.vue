<template>
    <v-app id="keep">
        <v-app-bar app clipped-left color="teal" dark>
            <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
            <span class="title ml-3 mr-5">Robot&nbsp;Monitoring</span>
            <v-spacer />
            <v-menu
      bottom
      left
      offset-y
      origin="top right"
      transition="scale-transition"
    >
      <template v-slot:activator="{ attrs, on }">
        <v-btn
          class="ml-2"
          min-width="0"
          text
          v-bind="attrs"
          v-on="on"
        >
          <v-badge
            color="red"
            overlap
            v-if="isDoing"
          >
            <template v-slot:badge>
              <span>{{doingCnt}}</span>
            </template>

            <v-icon>mdi-bell</v-icon>
          </v-badge>
        </v-btn>
      </template>

      <v-list dense>
        <v-subheader>Remaining Work</v-subheader>
          <v-list-item
            v-for="(item, i) in doing"
            :key="i"
          >
            <v-list-item-content>
              <v-list-item-title v-text="item"></v-list-item-title>
            </v-list-item-content>
          </v-list-item>
      </v-list>
    </v-menu>
            <span class="ml-3 mr-5">We Finance Global Korea</span>
        </v-app-bar>
    
        <v-navigation-drawer v-model="drawer" app clipped color="grey lighten-4">
            <v-list dense class="grey lighten-4">
                <template v-for="(item, i) in items">
                  <v-row
                    v-if="item.heading"
                    :key="i"
                    align="center"
                  >
                  </v-row>
                  <v-divider
                    v-else-if="item.divider"
                    :key="i"
                    dark
                    class="my-4"
                  />
                  <v-list-item
                    v-else
                    :key="i"
                    :to="item.path"
                  >
                  <v-list-item-action >
                    <v-icon style="font-size:28px;">{{ item.icon }}</v-icon>
                  </v-list-item-action>
                    <v-list-item-content>
                      <v-list-item-title style="font-size:15px;" class="grey--text">
                        {{ item.text }}
                      </v-list-item-title>
                    </v-list-item-content>
                  </v-list-item>
                </template>
             </v-list>
          </v-navigation-drawer>
    <v-content>
      <v-container
        fluid
        class="grey lighten-4 fill-height"
      >
        <router-view></router-view>
      </v-container>
    </v-content>
  </v-app>
</template>

<script>

const moment = require('moment')
var startDate = moment().format('YYYY-MM-DD');
export default {
    props: {
        source: String,
    },
    watch: {
      group () {
        this.drawer = false
      },
    },
    data: () => ({
        drawer: null,
        isDoing: false,
        startdate: startDate,
        doingCnt: 0,
        items: [
            { path: "/", icon: 'dashboard', text: 'Dashboard' },
            { path: "/dailyPlan", icon: 'dynamic_feed', text: 'To-do list' },
            { path: "/dailyReport", icon: 'content_paste', text: 'Daily Report' },
            { path: "/dataChart", icon: 'multiline_chart', text: 'Data Chart' },
            { path: "/kpi", icon: 'insights', text: 'KPI' },
            { path: "/history", icon: 'history', text: 'History Table' },
            { path: "/statistics", icon: 'insert_chart_outlined', text: 'Statistics Table' },
            { path: "/queue", icon: 'queue', text: 'WLM - Queue' },
             { divider: true },
            { path: "/bots", icon: 'android', text: 'Bot Management' },
            { path: "/jobs", icon: 'library_books', text: 'Job Management' },
            { path: "/steps", icon: 'format_list_numbered_rtl', text: 'Step Management' },
            { path: "/codes", icon: 'data_usage', text: 'Code Management' },
            { path: "/historyManagement", icon: 'perm_data_setting', text: 'History Management' },
        ],
        doing: [],
        polling: null
    }),
    mounted() {
        this.getData()
        this.pollData ()
    },
    methods: {
        getData() {
            this.axios.get(this.$store.state.rOpsApi+'/dailyPlan/doing?searchDate=' + startDate).then(response => {
              this.doingCnt = response.data.length
              if (this.doingCnt > 0) {
                this.isDoing = true;
              }
              var k=0;
              for (var i=0; i<this.doingCnt; i++) {
                k++
                this.doing.push(k+". "+response.data[i].job_name)
              }
            })
        },
        pollData () {
            this.polling = setInterval(() => {
                this.axios.get(this.$store.state.rOpsApi+'/dailyPlan/doing?searchDate=' + startDate).then(response => {
                  this.doing = []
                  this.doingCnt = response.data.length
                  if (this.doingCnt > 0) {
                    this.isDoing = true;
                  }
                  var k=0;
                  for (var i=0; i<this.doingCnt; i++) {
                    k++
                    this.doing.push(k+". "+response.data[i].job_name)
                  }
                })
            }, 60000)
        },
    },
    beforeDestroy () {
        clearInterval(this.polling)
    },
    created () {
        this.pollData()
    }
}
</script>

<style>
#keep .v-navigation-drawer__border {
    display: none
}
</style>