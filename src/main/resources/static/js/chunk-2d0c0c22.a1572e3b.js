(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d0c0c22"],{"42c1":function(t,e,a){"use strict";a.r(e);var s=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("v-layout",{attrs:{row:"",wrap:""}},[a("v-container",{attrs:{fluid:""}},[a("v-flex",{attrs:{xs12:""}},[a("v-card",[a("v-card-title",[a("v-toolbar-title",[t._v("RPA Logging History")]),a("v-btn",{attrs:{text:"",icon:"","x-large":"",color:"green"},on:{click:t.refresh}},[a("v-icon",[t._v("mdi-cached")])],1),a("v-spacer"),a("v-flex",{staticStyle:{"margin-right":"15px"},attrs:{xs12:"",sm2:""}},[a("v-select",{attrs:{items:t.bots,"item-text":"bot_id","item-value":"bot_id",label:"Select Bots",multiple:"",chips:"",dense:"","menu-props":{maxHeight:"540"}},on:{change:t.changeBots},scopedSlots:t._u([{key:"prepend-item",fn:function(){return[a("v-list-item",{attrs:{ripple:"",dense:""},on:{click:t.toggleBot}},[a("v-list-item-action",[a("v-icon",{attrs:{color:t.selectedBots.length>0?"indigo darken-4":""}},[t._v(t._s(t.iconBot))])],1),a("v-list-item-content",[a("v-list-item-title",[t._v("Select All")])],1)],1),a("v-divider",{staticClass:"mt-2"})]},proxy:!0},{key:"selection",fn:function(e){var s=e.item,o=e.index;return[0===o?a("v-chip",[a("span",[t._v(t._s(s.bot_id))])]):t._e(),1===o?a("v-chip",[a("span",[t._v(t._s(s.bot_id))])]):t._e(),2===o?a("span",{staticClass:"grey--text caption"},[t._v("(+"+t._s(t.selectedBots.length-2)+" others)")]):t._e()]}}]),model:{value:t.selectedBots,callback:function(e){t.selectedBots=e},expression:"selectedBots"}})],1),a("v-flex",{staticStyle:{"margin-right":"20px"},attrs:{xs12:"",sm3:""}},[a("v-select",{attrs:{items:t.jobs,"item-text":"job_name","item-value":"job_id",label:"Select Jobs",multiple:"",dense:"",chips:"","menu-props":{maxHeight:"540"}},on:{change:t.changeJobs},scopedSlots:t._u([{key:"prepend-item",fn:function(){return[a("v-list-item",{attrs:{ripple:"",dense:""},on:{click:t.toggleJob}},[a("v-list-item-action",[a("v-icon",{attrs:{color:t.jobsSelected.length>0?"indigo darken-4":""}},[t._v(t._s(t.iconJob))])],1),a("v-list-item-content",[a("v-list-item-title",[t._v("Select All")])],1)],1),a("v-divider",{staticClass:"mt-2"})]},proxy:!0},{key:"selection",fn:function(e){var s=e.item,o=e.index;return[0===o?a("v-chip",[a("span",[t._v(t._s(s.job_name))])]):t._e(),1===o?a("span",{staticClass:"grey--text caption"},[t._v("(+"+t._s(t.jobsSelected.length-1)+" others)")]):t._e()]}}]),model:{value:t.jobsSelected,callback:function(e){t.jobsSelected=e},expression:"jobsSelected"}})],1),a("v-flex",{attrs:{xs12:"",sm1:""}},[a("v-menu",{attrs:{"close-on-content-click":!1,"nudge-right":40,lazy:"",transition:"scale-transition","offset-y":"","full-width":"","min-width":"290px"},scopedSlots:t._u([{key:"activator",fn:function(e){var s=e.on;return[a("v-text-field",t._g({attrs:{label:"Start Date","prepend-icon":"event",readonly:""},model:{value:t.startdate,callback:function(e){t.startdate=e},expression:"startdate"}},s))]}}]),model:{value:t.datePicker_1,callback:function(e){t.datePicker_1=e},expression:"datePicker_1"}},[a("v-date-picker",{on:{input:function(e){t.datePicker_1=!1},change:t.changeStartDate},model:{value:t.startdate,callback:function(e){t.startdate=e},expression:"startdate"}})],1)],1),a("div",{staticStyle:{"margin-right":"6px"}},[t._v("~")]),a("v-flex",{attrs:{xs12:"",sm1:""}},[a("v-menu",{attrs:{"close-on-content-click":!1,"nudge-right":40,lazy:"",transition:"scale-transition","offset-y":"","full-width":"","min-width":"290px"},scopedSlots:t._u([{key:"activator",fn:function(e){var s=e.on;return[a("v-text-field",t._g({attrs:{label:"End Date","prepend-icon":"event",readonly:""},model:{value:t.enddate,callback:function(e){t.enddate=e},expression:"enddate"}},s))]}}]),model:{value:t.datePicker_2,callback:function(e){t.datePicker_2=e},expression:"datePicker_2"}},[a("v-date-picker",{on:{input:function(e){t.datePicker_2=!1},change:t.changeEndDate},model:{value:t.enddate,callback:function(e){t.enddate=e},expression:"enddate"}})],1)],1)],1),a("v-data-table",{attrs:{headers:t.headers,items:t.gridData_History,dense:"","footer-props":{"items-per-page-options":[20,30,40,-1]},"items-per-page":30},scopedSlots:t._u([{key:"item.seq_no",fn:function(e){var s=e.item;return[a("v-chip",{attrs:{color:t.getColor(s.seq_no),dark:""}},[t._v(t._s(s.seq_no))])]}},{key:"no-gridData_History",fn:function(){return[a("v-alert",{attrs:{value:!0,color:"error",icon:"warning"}},[t._v(' Your search for "'+t._s(t.search)+'" found no gridData_History. ')])]},proxy:!0}])})],1)],1)],1)],1)},o=[],i=(a("fb6a"),a("c1df")),n="",r="",l=i().subtract(1,"days").format("YYYY-MM-DD"),c=i().format("YYYY-MM-DD"),d="",u="",b="#C0AD8B",h="",v={data:function(){return{startdate:l,enddate:c,datePicker_1:!1,datePicker_2:!1,headers:[{text:"Bot ID",value:"bot_id",sortable:!1},{text:"Job Name",value:"job_name",sortable:!1},{text:"Seq",value:"seq_no",align:"end",sortable:!1},{text:"Step ID",value:"step_id",sortable:!1},{text:"Step Name",value:"step_name",sortable:!1},{text:"Status",value:"code_name",sortable:!1},{text:"Run Date",value:"run_date",sortable:!1},{text:"Elapsed Time",value:"elapsedTime",sortable:!1},{text:"Description",value:"description",sortable:!1}],pagination:{rowsPerPage:10},gridData_History:[],bots:[],selectedBots:[],jobs:[],jobsSelected:[]}},mounted:function(){var t=this;this.axios.get(this.$store.state.rOpsApi+"/bots").then((function(e){for(var a in t.bots=e.data,t.selectedBots=e.data,t.selectedBots)0==a?n=t.selectedBots[a].bot_id:n+=","+t.selectedBots[a].bot_id;t.axios.get(t.$store.state.rOpsApi+"/jobs/selectbox").then((function(e){for(var a in t.jobs=e.data,t.jobsSelected=e.data,t.jobsSelected)0==a?r=t.jobsSelected[a].job_id:r+=","+t.jobsSelected[a].job_id;t.axios.get(t.$store.state.rOpsApi+"/logging?bots="+n+"&jobs="+r+"&startDate="+l+"&endDate="+c).then((function(e){t.gridData_History=e.data}))}))}))},methods:{refresh:function(){var t=this;this.axios.get(this.$store.state.rOpsApi+"/logging?bots="+n+"&jobs="+r+"&startDate="+l+"&endDate="+c).then((function(e){t.gridData_History=e.data}))},changeBots:function(t){var e=this;n=t,this.axios.get(this.$store.state.rOpsApi+"/logging?bots="+n+"&jobs="+r+"&startDate="+l+"&endDate="+c).then((function(t){e.gridData_History=t.data}))},changeJobs:function(t){var e=this;r=t,this.axios.get(this.$store.state.rOpsApi+"/logging?bots="+n+"&jobs="+r+"&startDate="+l+"&endDate="+c).then((function(t){e.gridData_History=t.data}))},changeStartDate:function(t){var e=this;l=t,this.axios.get(this.$store.state.rOpsApi+"/logging?bots="+n+"&jobs="+r+"&startDate="+l+"&endDate="+c).then((function(t){e.gridData_History=t.data}))},changeEndDate:function(t){var e=this;c=t,this.axios.get(this.$store.state.rOpsApi+"/logging?bots="+n+"&jobs="+r+"&startDate="+l+"&endDate="+c).then((function(t){e.gridData_History=t.data}))},toggleJob:function(){var t=this;this.$nextTick((function(){for(var e in r="",t.allJob?t.jobsSelected=[]:t.jobsSelected=t.jobs.slice(),t.jobsSelected)""==r?r=t.jobsSelected[e].job_id:r+=","+t.jobsSelected[e].job_id;t.changeJobs(r)}))},toggleBot:function(){var t=this;this.$nextTick((function(){for(var e in n="",t.allBot?t.selectedBots=[]:t.selectedBots=t.bots.slice(),t.selectedBots)""==n?n=t.selectedBots[e].bot_id:n+=","+t.selectedBots[e].bot_id;t.changeBots(n)}))},getColor:function(t){return""==d&&(d=t),u=t,h=d!=u?"Y":"N","Y"==h&&(b="#C0AD8B"==b?"#7E776A":"#C0AD8B"),d=t,b}},computed:{allJob:function(){return this.jobsSelected.length===this.jobs.length},someJob:function(){return this.jobsSelected.length>0&&!this.allJob},iconJob:function(){return this.allJob?"mdi-close-box":this.someJob?"mdi-minus-box":"mdi-checkbox-blank-outline"},allBot:function(){return this.selectedBots.length===this.bots.length},someBot:function(){return this.selectedBots.length>0&&!this.allBot},iconBot:function(){return this.allBot?"mdi-close-box":this.someBot?"mdi-minus-box":"mdi-checkbox-blank-outline"}}},g=v,p=a("2877"),f=a("6544"),m=a.n(f),_=a("0798"),x=a("8336"),k=a("b0af"),D=a("99d9"),j=a("cc20"),S=a("a523"),y=a("8fea"),B=a("2e4b"),V=a("ce7e"),A=a("0e8f"),J=a("132d"),w=a("a722"),C=a("da13"),P=a("1800"),H=a("5d23"),Y=a("e449"),$=a("b974"),T=a("2fa4"),O=a("8654"),I=a("2a7f"),E=Object(p["a"])(g,s,o,!1,null,"721493d8",null);e["default"]=E.exports;m()(E,{VAlert:_["a"],VBtn:x["a"],VCard:k["a"],VCardTitle:D["c"],VChip:j["a"],VContainer:S["a"],VDataTable:y["a"],VDatePicker:B["a"],VDivider:V["a"],VFlex:A["a"],VIcon:J["a"],VLayout:w["a"],VListItem:C["a"],VListItemAction:P["a"],VListItemContent:H["a"],VListItemTitle:H["c"],VMenu:Y["a"],VSelect:$["a"],VSpacer:T["a"],VTextField:O["a"],VToolbarTitle:I["a"]})}}]);
//# sourceMappingURL=chunk-2d0c0c22.a1572e3b.js.map