(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-4cd361ae"],{"0e8f":function(t,a,e){"use strict";e("20f6");var i=e("e8f2");a["a"]=Object(i["a"])("flex")},"15fb":function(t,a,e){"use strict";e.r(a);var i=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("v-layout",{attrs:{row:"",wrap:""}},[e("v-container",{attrs:{fluid:""}},[e("v-flex",{staticStyle:{"margin-top":"10px"},attrs:{xs12:""}},[e("v-row",[e("v-col",{attrs:{cols:"12",lg:"6"}},[e("base-material-card",{staticClass:"px-5 py-3",attrs:{color:"#4CAF50"},scopedSlots:t._u([{key:"heading",fn:function(){return[e("div",{staticClass:"display-2 font-weight-light",staticStyle:{"font-size":"30px!important","margin-top":"-15px"}},[t._v(" To Do ")])]},proxy:!0}])},[e("v-card-text",[e("v-data-table",{staticStyle:{"margin-top":"-30px"},attrs:{headers:t.headers_1,items:t.gridData_1,"footer-props":{"items-per-page-options":[10,20,30,-1]},"items-per-page":-1,"hide-default-footer":""},scopedSlots:t._u([{key:"items",fn:function(a){return[e("td",{staticStyle:{"text-align":"left"}},[t._v(t._s(a.item.schedule_name))]),e("td",[t._v(t._s(a.item.schedule_time))])]}}])})],1)],1),e("v-fab-transition",[e("v-btn",{staticStyle:{"margin-top":"-30px"},attrs:{fab:"",large:"",dark:"",bottom:"",left:""},on:{click:function(a){t.dialog=!t.dialog}}},[e("v-icon",[t._v("mdi-calendar-cursor")])],1)],1)],1),e("v-col",{attrs:{cols:"12",lg:"6"}},[e("base-material-card",{staticClass:"px-5 py-3",attrs:{color:"#00BCD4"},scopedSlots:t._u([{key:"heading",fn:function(){return[e("div",{staticClass:"display-2 font-weight-light",staticStyle:{"font-size":"30px!important","margin-top":"-15px"}},[t._v(" Done ")])]},proxy:!0}])},[e("v-card-text",[e("v-data-table",{staticStyle:{"margin-top":"-30px"},attrs:{headers:t.headers_3,items:t.gridData_3,"footer-props":{"items-per-page-options":[10,20,30,-1]},"items-per-page":-1,"hide-default-footer":""},scopedSlots:t._u([{key:"items",fn:function(a){return[e("td",{staticStyle:{"text-align":"left"}},[t._v(t._s(a.item.job_name))]),e("td",[t._v(t._s(a.item.run_date))])]}}])})],1)],1),e("base-material-card",{staticClass:"px-5 py-3",staticStyle:{"margin-top":"10px"},attrs:{color:"#FF5722"},scopedSlots:t._u([{key:"heading",fn:function(){return[e("div",{staticClass:"display-2 font-weight-light",staticStyle:{"font-size":"30px!important","margin-top":"-15px"}},[t._v(" Doing ")])]},proxy:!0}])},[e("v-card-text",[e("v-data-table",{staticStyle:{"margin-top":"-30px"},attrs:{headers:t.headers_2,items:t.gridData_2,"footer-props":{"items-per-page-options":[10,20,30,-1]},"items-per-page":-1,"hide-default-footer":""},scopedSlots:t._u([{key:"item.job_name",fn:function(a){var i=a.item;return[e("v-chip",{attrs:{color:t.getColor(i.run_date)}},[t._v(t._s(i.job_name))])]}}])})],1)],1)],1)],1)],1),e("v-dialog",{attrs:{"max-width":"400px"},model:{value:t.dialog,callback:function(a){t.dialog=a},expression:"dialog"}},[e("v-card",[e("v-card-title",[t._v(" Search Date ")]),e("v-card-text",[e("v-row",[e("v-col",{attrs:{cols:"12",lg:"12"}},[e("v-menu",{attrs:{"close-on-content-click":!1,"nudge-right":40,lazy:"",transition:"scale-transition","offset-y":"","full-width":"","min-width":"290px"},scopedSlots:t._u([{key:"activator",fn:function(a){var i=a.on;return[e("v-text-field",t._g({attrs:{label:"Search Date","prepend-icon":"event",readonly:""},model:{value:t.startdate,callback:function(a){t.startdate=a},expression:"startdate"}},i))]}}]),model:{value:t.datePicker_1,callback:function(a){t.datePicker_1=a},expression:"datePicker_1"}},[e("v-date-picker",{on:{input:function(a){t.datePicker_1=!1},change:t.changeStartDate},model:{value:t.startdate,callback:function(a){t.startdate=a},expression:"startdate"}})],1)],1)],1)],1),e("v-card-actions",{staticStyle:{"margin-top":"-30px"}},[e("v-spacer"),e("v-btn",{attrs:{text:"",color:"primary"},on:{click:function(a){t.dialog=!1}}},[t._v(" CLOSE ")])],1)],1)],1)],1)],1)},o=[],n=e("c1df"),r=n().format("YYYY-MM-DD"),s={data:function(){return{startdate:r,datePicker_1:!1,datePicker_2:!1,dialog:!1,headers_1:[{text:"Schedule Name",value:"schedule_name",align:"start"},{text:"Schedule Time",value:"schedule_time"}],gridData_1:[],headers_2:[{text:"Job Name",value:"job_name"},{text:"Schedule Time",value:"schedule_time"},{text:"Log Time",value:"run_date"}],gridData_2:[],headers_3:[{text:"Job Name",value:"job_name"},{text:"Log Time",value:"run_date"}],gridData_3:[],polling:null}},mounted:function(){this.getData(),this.pollData()},methods:{getData:function(){var t=this;this.axios.get(this.$store.state.rOpsApi+"/dailyPlan/todo?searchDate="+r).then((function(a){t.gridData_1=a.data})),this.axios.get(this.$store.state.rOpsApi+"/dailyPlan/doing?searchDate="+r).then((function(a){t.gridData_2=a.data})),this.axios.get(this.$store.state.rOpsApi+"/dailyPlan/done?searchDate="+r).then((function(a){t.gridData_3=a.data}))},pollData:function(){var t=this;this.polling=setInterval((function(){t.axios.get(t.$store.state.rOpsApi+"/dailyPlan/todo?searchDate="+r).then((function(a){t.gridData_1=a.data})),t.axios.get(t.$store.state.rOpsApi+"/dailyPlan/doing?searchDate="+r).then((function(a){t.gridData_2=a.data})),t.axios.get(t.$store.state.rOpsApi+"/dailyPlan/done?searchDate="+r).then((function(a){t.gridData_3=a.data}))}),6e4)},changeStartDate:function(t){r=t,this.getData()},getColor:function(t){return null==t?"#B2DFDB":"#FF8A80"}},beforeDestroy:function(){clearInterval(this.polling)},created:function(){this.pollData()}},l=s,c=(e("2b73"),e("2877")),d=e("6544"),p=e.n(d),u=e("8336"),f=e("b0af"),g=e("99d9"),h=e("cc20"),m=e("62ad"),v=e("a523"),_=e("8fea"),x=e("2e4b"),y=e("169a"),D=e("0789"),b=e("0e8f"),S=e("132d"),k=e("a722"),V=e("e449"),C=e("0fd9"),w=e("2fa4"),P=e("8654"),T=Object(c["a"])(l,i,o,!1,null,null,null);a["default"]=T.exports;p()(T,{VBtn:u["a"],VCard:f["a"],VCardActions:g["a"],VCardText:g["b"],VCardTitle:g["c"],VChip:h["a"],VCol:m["a"],VContainer:v["a"],VDataTable:_["a"],VDatePicker:x["a"],VDialog:y["a"],VFabTransition:D["c"],VFlex:b["a"],VIcon:S["a"],VLayout:k["a"],VMenu:V["a"],VRow:C["a"],VSpacer:w["a"],VTextField:P["a"]})},"2b73":function(t,a,e){"use strict";e("f8d9")},f8d9:function(t,a,e){}}]);
//# sourceMappingURL=chunk-4cd361ae.80179e38.js.map