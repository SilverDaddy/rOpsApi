(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-a2732a9e"],{"08d7":function(t,a,e){"use strict";e("dcc0")},"169a":function(t,a,e){"use strict";e("7db0"),e("caad"),e("45fc"),e("a9e3"),e("2532"),e("498a");var i=e("5530"),n=e("2909"),s=e("ade3"),o=(e("368e"),e("480e")),c=e("4ad4"),r=e("b848"),l=e("75eb"),d=e("e707"),u=e("e4d3"),h=e("21be"),f=e("f2e7"),p=e("a293"),b=e("58df"),v=e("d9bd"),g=e("80d2"),m=Object(b["a"])(c["a"],r["a"],l["a"],d["a"],u["a"],h["a"],f["a"]);a["a"]=m.extend({name:"v-dialog",directives:{ClickOutside:p["a"]},props:{dark:Boolean,disabled:Boolean,fullscreen:Boolean,light:Boolean,maxWidth:{type:[String,Number],default:"none"},noClickAnimation:Boolean,origin:{type:String,default:"center center"},persistent:Boolean,retainFocus:{type:Boolean,default:!0},scrollable:Boolean,transition:{type:[String,Boolean],default:"dialog-transition"},width:{type:[String,Number],default:"auto"}},data:function(){return{activatedBy:null,animate:!1,animateTimeout:-1,isActive:!!this.value,stackMinZIndex:200,previousActiveElement:null}},computed:{classes:function(){var t;return t={},Object(s["a"])(t,"v-dialog ".concat(this.contentClass).trim(),!0),Object(s["a"])(t,"v-dialog--active",this.isActive),Object(s["a"])(t,"v-dialog--persistent",this.persistent),Object(s["a"])(t,"v-dialog--fullscreen",this.fullscreen),Object(s["a"])(t,"v-dialog--scrollable",this.scrollable),Object(s["a"])(t,"v-dialog--animated",this.animate),t},contentClasses:function(){return{"v-dialog__content":!0,"v-dialog__content--active":this.isActive}},hasActivator:function(){return Boolean(!!this.$slots.activator||!!this.$scopedSlots.activator)}},watch:{isActive:function(t){var a;t?(this.show(),this.hideScroll()):(this.removeOverlay(),this.unbind(),null==(a=this.previousActiveElement)||a.focus())},fullscreen:function(t){this.isActive&&(t?(this.hideScroll(),this.removeOverlay(!1)):(this.showScroll(),this.genOverlay()))}},created:function(){this.$attrs.hasOwnProperty("full-width")&&Object(v["e"])("full-width",this)},beforeMount:function(){var t=this;this.$nextTick((function(){t.isBooted=t.isActive,t.isActive&&t.show()}))},beforeDestroy:function(){"undefined"!==typeof window&&this.unbind()},methods:{animateClick:function(){var t=this;this.animate=!1,this.$nextTick((function(){t.animate=!0,window.clearTimeout(t.animateTimeout),t.animateTimeout=window.setTimeout((function(){return t.animate=!1}),150)}))},closeConditional:function(t){var a=t.target;return!(this._isDestroyed||!this.isActive||this.$refs.content.contains(a)||this.overlay&&a&&!this.overlay.$el.contains(a))&&this.activeZIndex>=this.getMaxZIndex()},hideScroll:function(){this.fullscreen?document.documentElement.classList.add("overflow-y-hidden"):d["a"].options.methods.hideScroll.call(this)},show:function(){var t=this;!this.fullscreen&&!this.hideOverlay&&this.genOverlay(),this.$nextTick((function(){t.$nextTick((function(){t.previousActiveElement=document.activeElement,t.$refs.content.focus(),t.bind()}))}))},bind:function(){window.addEventListener("focusin",this.onFocusin)},unbind:function(){window.removeEventListener("focusin",this.onFocusin)},onClickOutside:function(t){this.$emit("click:outside",t),this.persistent?this.noClickAnimation||this.animateClick():this.isActive=!1},onKeydown:function(t){if(t.keyCode===g["x"].esc&&!this.getOpenDependents().length)if(this.persistent)this.noClickAnimation||this.animateClick();else{this.isActive=!1;var a=this.getActivator();this.$nextTick((function(){return a&&a.focus()}))}this.$emit("keydown",t)},onFocusin:function(t){if(t&&this.retainFocus){var a=t.target;if(a&&![document,this.$refs.content].includes(a)&&!this.$refs.content.contains(a)&&this.activeZIndex>=this.getMaxZIndex()&&!this.getOpenDependentElements().some((function(t){return t.contains(a)}))){var e=this.$refs.content.querySelectorAll('button, [href], input, select, textarea, [tabindex]:not([tabindex="-1"])'),i=Object(n["a"])(e).find((function(t){return!t.hasAttribute("disabled")}));i&&i.focus()}}},genContent:function(){var t=this;return this.showLazyContent((function(){return[t.$createElement(o["a"],{props:{root:!0,light:t.light,dark:t.dark}},[t.$createElement("div",{class:t.contentClasses,attrs:Object(i["a"])({role:"document",tabindex:t.isActive?0:void 0},t.getScopeIdAttrs()),on:{keydown:t.onKeydown},style:{zIndex:t.activeZIndex},ref:"content"},[t.genTransition()])])]}))},genTransition:function(){var t=this.genInnerContent();return this.transition?this.$createElement("transition",{props:{name:this.transition,origin:this.origin,appear:!0}},[t]):t},genInnerContent:function(){var t={class:this.classes,ref:"dialog",directives:[{name:"click-outside",value:{handler:this.onClickOutside,closeConditional:this.closeConditional,include:this.getOpenDependentElements}},{name:"show",value:this.isActive}],style:{transformOrigin:this.origin}};return this.fullscreen||(t.style=Object(i["a"])(Object(i["a"])({},t.style),{},{maxWidth:"none"===this.maxWidth?void 0:Object(g["g"])(this.maxWidth),width:"auto"===this.width?void 0:Object(g["g"])(this.width)})),this.$createElement("div",t,this.getContentSlot())}},render:function(t){return t("div",{staticClass:"v-dialog__container",class:{"v-dialog__container--attached":""===this.attach||!0===this.attach||"attach"===this.attach},attrs:{role:"dialog"}},[this.genActivator(),this.genContent()])}})},"1fca":function(t,a,e){"use strict";function i(t,a){if(a){var e=this.$data._chart,i=t.datasets.map((function(t){return t.label})),n=a.datasets.map((function(t){return t.label})),s=JSON.stringify(n),o=JSON.stringify(i);o===s&&a.datasets.length===t.datasets.length?(t.datasets.forEach((function(t,i){var n=Object.keys(a.datasets[i]),s=Object.keys(t),o=n.filter((function(t){return"_meta"!==t&&-1===s.indexOf(t)}));for(var c in o.forEach((function(t){delete e.data.datasets[i][t]})),t)t.hasOwnProperty(c)&&(e.data.datasets[i][c]=t[c])})),t.hasOwnProperty("labels")&&(e.data.labels=t.labels,this.$emit("labels:update")),t.hasOwnProperty("xLabels")&&(e.data.xLabels=t.xLabels,this.$emit("xlabels:update")),t.hasOwnProperty("yLabels")&&(e.data.yLabels=t.yLabels,this.$emit("ylabels:update")),e.update(),this.$emit("chart:update")):(e&&(e.destroy(),this.$emit("chart:destroy")),this.renderChart(this.chartData,this.options),this.$emit("chart:render"))}else this.$data._chart&&(this.$data._chart.destroy(),this.$emit("chart:destroy")),this.renderChart(this.chartData,this.options),this.$emit("chart:render")}e.d(a,"a",(function(){return d})),e.d(a,"c",(function(){return u})),e.d(a,"b",(function(){return h})),e.d(a,"d",(function(){return f})),e.d(a,"e",(function(){return o}));var n={data:function(){return{chartData:null}},watch:{chartData:i}},s={props:{chartData:{type:Object,required:!0,default:function(){}}},watch:{chartData:i}},o={reactiveData:n,reactiveProp:s},c=e("30ef"),r=e.n(c);function l(t,a){return{render:function(t){return t("div",{style:this.styles,class:this.cssClasses},[t("canvas",{attrs:{id:this.chartId,width:this.width,height:this.height},ref:"canvas"})])},props:{chartId:{default:t,type:String},width:{default:400,type:Number},height:{default:400,type:Number},cssClasses:{type:String,default:""},styles:{type:Object},plugins:{type:Array,default:function(){return[]}}},data:function(){return{_chart:null,_plugins:this.plugins}},methods:{addPlugin:function(t){this.$data._plugins.push(t)},generateLegend:function(){if(this.$data._chart)return this.$data._chart.generateLegend()},renderChart:function(t,e){if(this.$data._chart&&this.$data._chart.destroy(),!this.$refs.canvas)throw new Error("Please remove the <template></template> tags from your chart component. See https://vue-chartjs.org/guide/#vue-single-file-components");this.$data._chart=new r.a(this.$refs.canvas.getContext("2d"),{type:a,data:t,options:e,plugins:this.$data._plugins})}},beforeDestroy:function(){this.$data._chart&&this.$data._chart.destroy()}}}var d=l("bar-chart","bar"),u=l("horizontalbar-chart","horizontalBar"),h=l("doughnut-chart","doughnut"),f=l("line-chart","line");l("pie-chart","pie"),l("polar-chart","polarArea"),l("radar-chart","radar"),l("bubble-chart","bubble"),l("scatter-chart","scatter")},"368e":function(t,a,e){},aac6:function(t,a,e){"use strict";e.r(a);var i,n,s=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("v-layout",{attrs:{row:"",wrap:""}},[e("v-container",{attrs:{fluid:""}},[e("v-flex",{staticStyle:{"margin-top":"10px"},attrs:{xs12:""}},[e("v-row",[e("v-col",{attrs:{cols:"12",lg:"3"}},[e("base-material-stats-card",{attrs:{color:"blue",icon:"mdi-alpha-t-circle-outline",title:"Total Count"},model:{value:t.count_total,callback:function(a){t.count_total=a},expression:"count_total"}}),e("base-material-stats-card",{staticStyle:{"margin-top":"20px"},attrs:{color:"success",icon:"mdi-check-circle-outline",title:"Success Count"},model:{value:t.count_success,callback:function(a){t.count_success=a},expression:"count_success"}}),e("base-material-stats-card",{staticStyle:{"margin-top":"20px"},attrs:{color:"red",icon:"mdi-alert-circle-outline",title:"Fail Count"},model:{value:t.count_fail,callback:function(a){t.count_fail=a},expression:"count_fail"}}),e("base-material-stats-card",{staticStyle:{"margin-top":"20px"},attrs:{color:"orange",icon:"mdi-strategy",title:"Success Rate"},model:{value:t.rate_success,callback:function(a){t.rate_success=a},expression:"rate_success"}}),e("v-card",{staticClass:"mx-auto",staticStyle:{"margin-top":"20px"},attrs:{"max-width":"400"}},[e("v-card-text",[e("v-row",{attrs:{align:"center"}},[e("v-col",{staticClass:"display-5",attrs:{cols:"6"}},[e("v-text-field",{staticClass:"display-5",attrs:{value:t.startdate,readonly:"",disabled:"",label:"Start Date"}})],1),e("v-col",{attrs:{cols:"6"}},[e("v-text-field",{staticClass:"display-5",attrs:{value:t.enddate,readonly:"",disabled:"",label:"End Date"}})],1)],1)],1)],1),e("v-fab-transition",[e("v-btn",{staticStyle:{"margin-top":"-30px"},attrs:{fab:"",large:"",dark:"",bottom:"",left:""},on:{click:function(a){t.dialog=!t.dialog}}},[e("v-icon",[t._v("mdi-calendar-cursor")])],1)],1)],1),e("v-col",{attrs:{cols:"12",lg:"4"}},[e("base-material-card",{staticClass:"px-5 py-3",attrs:{color:"#8D6E63"},scopedSlots:t._u([{key:"heading",fn:function(){return[e("div",{staticClass:"display-2 font-weight-light",staticStyle:{"font-size":"30px!important","margin-top":"-15px"}},[t._v(" Bot Stats ")])]},proxy:!0}])},[e("v-card-text",[e("v-data-table",{staticStyle:{"margin-top":"-20px"},attrs:{headers:t.headers_Bot,items:t.gridData_Bot,dense:"","footer-props":{"items-per-page-options":[10,20,30,-1]},"items-per-page":-1,"hide-default-footer":""},scopedSlots:t._u([{key:"items",fn:function(a){return[e("td",[t._v(t._s(a.item.bot_name)+"("+t._s(a.item.bot_id)+")")]),e("td",{staticStyle:{"text-align":"right"}},[t._v(t._s(a.item.count_total))]),e("td",[t._v(t._s(a.item.totaluptime))])]}}])})],1)],1),e("base-material-card",{staticClass:"px-5 py-3",staticStyle:{"margin-top":"20px"},attrs:{color:"#FFAB40"},scopedSlots:t._u([{key:"heading",fn:function(){return[e("div",{staticClass:"display-2 font-weight-light",staticStyle:{"font-size":"30px!important","margin-top":"-15px"}},[t._v(" Bot Utilization Rate ")])]},proxy:!0}])},[e("v-card-text",[e("div",{staticClass:"column"},[t.chartLoading?e("div",{staticClass:"text-lg-center pa-5",staticStyle:{width:"100%"}},[e("v-progress-circular",{attrs:{width:"7",size:"70",indeterminate:"",color:"red"}})],1):t._e(),t.chartLoading?t._e():e("doughnut-chart",{attrs:{"chart-data":t.chartData}})],1)])],1)],1),e("v-col",{attrs:{cols:"12",lg:"5"}},[e("base-material-card",{staticClass:"px-5 py-3",attrs:{color:"#78909C"},scopedSlots:t._u([{key:"heading",fn:function(){return[e("div",{staticClass:"display-2 font-weight-light",staticStyle:{"font-size":"30px!important","margin-top":"-15px"}},[t._v(" Job Stats ")])]},proxy:!0}])},[e("v-card-text",[e("v-data-table",{staticStyle:{"margin-top":"-20px"},attrs:{headers:t.headers_Job,items:t.gridData_Job,dense:"","footer-props":{"items-per-page-options":[10,20,30,-1]},"items-per-page":-1,"hide-default-footer":""}})],1)],1)],1)],1)],1),e("v-dialog",{attrs:{"max-width":"400px"},model:{value:t.dialog,callback:function(a){t.dialog=a},expression:"dialog"}},[e("v-card",[e("v-card-title",[t._v(" Search Date ")]),e("v-card-text",[e("v-row",[e("v-col",{attrs:{cols:"12",lg:"6"}},[e("v-menu",{attrs:{"close-on-content-click":!1,"nudge-right":40,lazy:"",transition:"scale-transition","offset-y":"","full-width":"","min-width":"290px"},scopedSlots:t._u([{key:"activator",fn:function(a){var i=a.on;return[e("v-text-field",t._g({attrs:{label:"Start Date","prepend-icon":"event",readonly:""},model:{value:t.startdate,callback:function(a){t.startdate=a},expression:"startdate"}},i))]}}]),model:{value:t.datePicker_1,callback:function(a){t.datePicker_1=a},expression:"datePicker_1"}},[e("v-date-picker",{on:{input:function(a){t.datePicker_1=!1},change:t.changeStartDate},model:{value:t.startdate,callback:function(a){t.startdate=a},expression:"startdate"}})],1)],1),e("v-col",{attrs:{cols:"12",lg:"6"}},[e("v-menu",{attrs:{"close-on-content-click":!1,"nudge-right":40,lazy:"",transition:"scale-transition","offset-y":"","full-width":"","min-width":"290px"},scopedSlots:t._u([{key:"activator",fn:function(a){var i=a.on;return[e("v-text-field",t._g({attrs:{label:"End Date","prepend-icon":"event",readonly:""},model:{value:t.enddate,callback:function(a){t.enddate=a},expression:"enddate"}},i))]}}]),model:{value:t.datePicker_2,callback:function(a){t.datePicker_2=a},expression:"datePicker_2"}},[e("v-date-picker",{on:{input:function(a){t.datePicker_2=!1},change:t.changeEndDate},model:{value:t.enddate,callback:function(a){t.enddate=a},expression:"enddate"}})],1)],1)],1)],1),e("v-card-actions",{staticStyle:{"margin-top":"-30px"}},[e("v-spacer"),e("v-btn",{attrs:{text:"",color:"primary"},on:{click:function(a){t.dialog=!1}}},[t._v(" CLOSE ")])],1)],1)],1)],1)],1)},o=[],c=(e("4160"),e("159b"),e("1fca")),r=c["e"].reactiveProp,l={name:"Doughnut",extends:c["b"],props:["chartData"],mixins:[r],data:function(){return{colorSets:[{back:"#ff5252"},{back:"#0779e4"},{back:"#1aa6b7"},{back:"#ffb396"},{back:"#d68060"},{back:"#a2d5f2"},{back:"#86aba1"},{back:"#ea97ad"},{back:"#583d72"},{back:"#ffe3d8"},{back:"#c75643"},{back:"#222831"},{back:"#16697a"},{back:"#fd3a69"},{back:"#8f384d"},{back:"#9ddfd3"},{back:"#835858"},{back:"#0278ae"},{back:"#87431d"},{back:"#ffc93c"},{back:"#8d93ab"},{back:"#424242"},{back:"#FF3D00"},{back:"#4E342E"},{back:"#37474F"},{back:"#FFC400"},{back:"#827717"},{back:"#2E7D32"},{back:"#40C4FF"},{back:"#CDDC39"},{back:"#004D40"},{back:"#7C4DFF"},{back:"#AD1457"},{back:"#BBDEFB"},{back:"#4A148C"},{back:"#9FA8DA"},{back:"#00BFA5"},{back:"#D4E157"},{back:"#7CB342"},{back:"#B9F6CA"},{back:"#D50000"},{back:"#D1C4E9"},{back:"#006064"}],options:{responsive:!0,legend:{display:!0},maintainAspectRatio:!1,cutoutPercentage:55,animation:!1,circumference:Math.PI,rotation:-Math.PI}}},mounted:function(){this.init()},methods:{init:function(){var t=this,a=this.chartData.chartData,e="%",i=[],n=[],s=0;a.forEach((function(e){i[s]=t.colorSets[s].back,a[s]=e,s++})),n.push({label:e,backgroundColor:i,data:a}),this.datacollection={labels:this.chartData.labels,datasets:n},this.render()},render:function(){this.renderChart(this.datacollection,this.options)}},watch:{chartData:function(){this.init()}}},d=l,u=e("2877"),h=Object(u["a"])(d,i,n,!1,null,null,null),f=h.exports,p=e("c1df"),b=p().subtract(1,"days").format("YYYY-MM-DD"),v=p().subtract(1,"days").format("YYYY-MM-DD"),g={components:{DoughnutChart:f},data:function(){return{startdate:b,enddate:v,datePicker_1:!1,datePicker_2:!1,dialog:!1,count_total:"0",count_success:"0",count_fail:"0",rate_success:"0 %",headers_Bot:[{text:"Bot",value:"bot_id"},{text:"Run Cnt",value:"count_total",align:"end"},{text:"Total Uptime",value:"totaluptime"}],gridData_Bot:[],headers_Job:[{text:"Job Name",value:"job_name"},{text:"Run Cnt",value:"count_total",align:"end"},{text:"Success Rate",value:"rate_success",align:"end"},{text:"Last Log Time",value:"run_date"}],gridData_Job:[],chartLoading:!0,chartData:[]}},mounted:function(){this.getData()},methods:{getData:function(){var t=this;this.axios.get(this.$store.state.rOpsApi+"/dailyReport/count?startDate="+b+"&endDate="+v).then((function(a){t.count_total=a.data[0].count_total+" EA",t.count_success=a.data[0].count_success+" EA",t.count_fail=a.data[0].count_fail+" EA",t.rate_success=a.data[0].rate_success+" %"})),this.axios.get(this.$store.state.rOpsApi+"/dailyReport/bot?startDate="+b+"&endDate="+v).then((function(a){t.gridData_Bot=a.data})),this.axios.get(this.$store.state.rOpsApi+"/dailyReport/bot/utilization?startDate="+b+"&endDate="+v).then((function(a){t.chartData={labels:a.data.labels,chartData:a.data.data},t.chartLoading=!1})),this.axios.get(this.$store.state.rOpsApi+"/dailyReport/job?startDate="+b+"&endDate="+v).then((function(a){t.gridData_Job=a.data}))},changeStartDate:function(t){b=t,this.getData()},changeEndDate:function(t){v=t,this.getData()}}},m=g,y=(e("08d7"),e("6544")),k=e.n(y),_=e("8336"),x=e("b0af"),D=e("99d9"),C=e("62ad"),w=e("a523"),S=e("8fea"),$=e("2e4b"),O=e("169a"),A=e("0789"),E=e("0e8f"),B=e("132d"),F=e("a722"),P=e("e449"),j=e("490a"),L=e("0fd9"),T=e("2fa4"),V=e("8654"),I=Object(u["a"])(m,s,o,!1,null,null,null);a["default"]=I.exports;k()(I,{VBtn:_["a"],VCard:x["a"],VCardActions:D["a"],VCardText:D["b"],VCardTitle:D["c"],VCol:C["a"],VContainer:w["a"],VDataTable:S["a"],VDatePicker:$["a"],VDialog:O["a"],VFabTransition:A["c"],VFlex:E["a"],VIcon:B["a"],VLayout:F["a"],VMenu:P["a"],VProgressCircular:j["a"],VRow:L["a"],VSpacer:T["a"],VTextField:V["a"]})},dcc0:function(t,a,e){}}]);
//# sourceMappingURL=chunk-a2732a9e.0e9fe43e.js.map