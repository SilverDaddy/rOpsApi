(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-5cca910c"],{"15fb":function(t,e,i){"use strict";i.r(e);var a=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("v-layout",{attrs:{row:"",wrap:""}},[i("v-container",{attrs:{fluid:""}},[i("v-flex",{staticStyle:{"margin-top":"10px"},attrs:{xs12:""}},[i("v-row",[i("v-col",{attrs:{cols:"12",lg:"6"}},[i("base-material-card",{staticClass:"px-5 py-3",attrs:{color:"#4CAF50"},scopedSlots:t._u([{key:"heading",fn:function(){return[i("div",{staticClass:"display-2 font-weight-light",staticStyle:{"font-size":"30px!important","margin-top":"-15px"}},[t._v(" To Do ")])]},proxy:!0}])},[i("v-card-text",[i("v-data-table",{staticStyle:{"margin-top":"-30px"},attrs:{headers:t.headers_1,items:t.gridData_1,"footer-props":{"items-per-page-options":[10,20,30,-1]},"items-per-page":-1,"hide-default-footer":""},scopedSlots:t._u([{key:"items",fn:function(e){return[i("td",{staticStyle:{"text-align":"left"}},[t._v(t._s(e.item.schedule_name))]),i("td",[t._v(t._s(e.item.schedule_time))])]}}])})],1)],1),i("v-fab-transition",[i("v-btn",{staticStyle:{"margin-top":"-30px"},attrs:{fab:"",large:"",dark:"",bottom:"",left:""},on:{click:function(e){t.dialog=!t.dialog}}},[i("v-icon",[t._v("mdi-calendar-cursor")])],1)],1)],1),i("v-col",{attrs:{cols:"12",lg:"6"}},[i("base-material-card",{staticClass:"px-5 py-3",attrs:{color:"#00BCD4"},scopedSlots:t._u([{key:"heading",fn:function(){return[i("div",{staticClass:"display-2 font-weight-light",staticStyle:{"font-size":"30px!important","margin-top":"-15px"}},[t._v(" Done ")])]},proxy:!0}])},[i("v-card-text",[i("v-data-table",{staticStyle:{"margin-top":"-30px"},attrs:{headers:t.headers_3,items:t.gridData_3,"footer-props":{"items-per-page-options":[10,20,30,-1]},"items-per-page":-1,"hide-default-footer":""},scopedSlots:t._u([{key:"items",fn:function(e){return[i("td",{staticStyle:{"text-align":"left"}},[t._v(t._s(e.item.job_name))]),i("td",[t._v(t._s(e.item.run_date))])]}}])})],1)],1),i("base-material-card",{staticClass:"px-5 py-3",staticStyle:{"margin-top":"10px"},attrs:{color:"#FF5722"},scopedSlots:t._u([{key:"heading",fn:function(){return[i("div",{staticClass:"display-2 font-weight-light",staticStyle:{"font-size":"30px!important","margin-top":"-15px"}},[t._v(" Doing ")])]},proxy:!0}])},[i("v-card-text",[i("v-data-table",{staticStyle:{"margin-top":"-30px"},attrs:{headers:t.headers_2,items:t.gridData_2,"footer-props":{"items-per-page-options":[10,20,30,-1]},"items-per-page":-1,"hide-default-footer":""},scopedSlots:t._u([{key:"item.job_name",fn:function(e){var a=e.item;return[i("v-chip",{attrs:{color:t.getColor(a.run_date)}},[t._v(t._s(a.job_name))])]}}])})],1)],1)],1)],1)],1),i("v-dialog",{attrs:{"max-width":"400px"},model:{value:t.dialog,callback:function(e){t.dialog=e},expression:"dialog"}},[i("v-card",[i("v-card-title",[t._v(" Search Date ")]),i("v-card-text",[i("v-row",[i("v-col",{attrs:{cols:"12",lg:"12"}},[i("v-menu",{attrs:{"close-on-content-click":!1,"nudge-right":40,lazy:"",transition:"scale-transition","offset-y":"","full-width":"","min-width":"290px"},scopedSlots:t._u([{key:"activator",fn:function(e){var a=e.on;return[i("v-text-field",t._g({attrs:{label:"Search Date","prepend-icon":"event",readonly:""},model:{value:t.startdate,callback:function(e){t.startdate=e},expression:"startdate"}},a))]}}]),model:{value:t.datePicker_1,callback:function(e){t.datePicker_1=e},expression:"datePicker_1"}},[i("v-date-picker",{on:{input:function(e){t.datePicker_1=!1},change:t.changeStartDate},model:{value:t.startdate,callback:function(e){t.startdate=e},expression:"startdate"}})],1)],1)],1)],1),i("v-card-actions",{staticStyle:{"margin-top":"-30px"}},[i("v-spacer"),i("v-btn",{attrs:{text:"",color:"primary"},on:{click:function(e){t.dialog=!1}}},[t._v(" CLOSE ")])],1)],1)],1)],1)],1)},n=[],s=i("c1df"),o=s().format("YYYY-MM-DD"),r={data:function(){return{startdate:o,datePicker_1:!1,datePicker_2:!1,dialog:!1,headers_1:[{text:"Schedule Name",value:"schedule_name",align:"start"},{text:"Schedule Time",value:"schedule_time"}],gridData_1:[],headers_2:[{text:"Job Name",value:"job_name"},{text:"Schedule Time",value:"schedule_time"},{text:"Log Time",value:"run_date"}],gridData_2:[],headers_3:[{text:"Job Name",value:"job_name"},{text:"Log Time",value:"run_date"}],gridData_3:[]}},mounted:function(){this.getData()},methods:{getData:function(){var t=this;this.axios.get(this.$store.state.rOpsApi+"/dailyPlan/todo?searchDate="+o).then((function(e){t.gridData_1=e.data})),this.axios.get(this.$store.state.rOpsApi+"/dailyPlan/doing?searchDate="+o).then((function(e){t.gridData_2=e.data})),this.axios.get(this.$store.state.rOpsApi+"/dailyPlan/done?searchDate="+o).then((function(e){t.gridData_3=e.data}))},changeStartDate:function(t){o=t,this.getData()},getColor:function(t){return null==t?"#B2DFDB":"#FF8A80"}}},c=r,l=(i("2b73"),i("2877")),d=i("6544"),u=i.n(d),h=i("8336"),f=i("b0af"),v=i("99d9"),p=i("cc20"),m=i("62ad"),g=i("a523"),x=i("8fea"),b=i("2e4b"),_=i("169a"),y=i("0789"),k=i("0e8f"),w=i("132d"),C=i("a722"),S=i("e449"),D=i("0fd9"),O=i("2fa4"),A=i("8654"),$=Object(l["a"])(c,a,n,!1,null,null,null);e["default"]=$.exports;u()($,{VBtn:h["a"],VCard:f["a"],VCardActions:v["a"],VCardText:v["b"],VCardTitle:v["c"],VChip:p["a"],VCol:m["a"],VContainer:g["a"],VDataTable:x["a"],VDatePicker:b["a"],VDialog:_["a"],VFabTransition:y["c"],VFlex:k["a"],VIcon:w["a"],VLayout:C["a"],VMenu:S["a"],VRow:D["a"],VSpacer:O["a"],VTextField:A["a"]})},"169a":function(t,e,i){"use strict";i("7db0"),i("caad"),i("45fc"),i("a9e3"),i("2532"),i("498a");var a=i("5530"),n=i("2909"),s=i("ade3"),o=(i("368e"),i("480e")),r=i("4ad4"),c=i("b848"),l=i("75eb"),d=i("e707"),u=i("e4d3"),h=i("21be"),f=i("f2e7"),v=i("a293"),p=i("58df"),m=i("d9bd"),g=i("80d2"),x=Object(p["a"])(r["a"],c["a"],l["a"],d["a"],u["a"],h["a"],f["a"]);e["a"]=x.extend({name:"v-dialog",directives:{ClickOutside:v["a"]},props:{dark:Boolean,disabled:Boolean,fullscreen:Boolean,light:Boolean,maxWidth:{type:[String,Number],default:"none"},noClickAnimation:Boolean,origin:{type:String,default:"center center"},persistent:Boolean,retainFocus:{type:Boolean,default:!0},scrollable:Boolean,transition:{type:[String,Boolean],default:"dialog-transition"},width:{type:[String,Number],default:"auto"}},data:function(){return{activatedBy:null,animate:!1,animateTimeout:-1,isActive:!!this.value,stackMinZIndex:200,previousActiveElement:null}},computed:{classes:function(){var t;return t={},Object(s["a"])(t,"v-dialog ".concat(this.contentClass).trim(),!0),Object(s["a"])(t,"v-dialog--active",this.isActive),Object(s["a"])(t,"v-dialog--persistent",this.persistent),Object(s["a"])(t,"v-dialog--fullscreen",this.fullscreen),Object(s["a"])(t,"v-dialog--scrollable",this.scrollable),Object(s["a"])(t,"v-dialog--animated",this.animate),t},contentClasses:function(){return{"v-dialog__content":!0,"v-dialog__content--active":this.isActive}},hasActivator:function(){return Boolean(!!this.$slots.activator||!!this.$scopedSlots.activator)}},watch:{isActive:function(t){var e;t?(this.show(),this.hideScroll()):(this.removeOverlay(),this.unbind(),null==(e=this.previousActiveElement)||e.focus())},fullscreen:function(t){this.isActive&&(t?(this.hideScroll(),this.removeOverlay(!1)):(this.showScroll(),this.genOverlay()))}},created:function(){this.$attrs.hasOwnProperty("full-width")&&Object(m["e"])("full-width",this)},beforeMount:function(){var t=this;this.$nextTick((function(){t.isBooted=t.isActive,t.isActive&&t.show()}))},beforeDestroy:function(){"undefined"!==typeof window&&this.unbind()},methods:{animateClick:function(){var t=this;this.animate=!1,this.$nextTick((function(){t.animate=!0,window.clearTimeout(t.animateTimeout),t.animateTimeout=window.setTimeout((function(){return t.animate=!1}),150)}))},closeConditional:function(t){var e=t.target;return!(this._isDestroyed||!this.isActive||this.$refs.content.contains(e)||this.overlay&&e&&!this.overlay.$el.contains(e))&&this.activeZIndex>=this.getMaxZIndex()},hideScroll:function(){this.fullscreen?document.documentElement.classList.add("overflow-y-hidden"):d["a"].options.methods.hideScroll.call(this)},show:function(){var t=this;!this.fullscreen&&!this.hideOverlay&&this.genOverlay(),this.$nextTick((function(){t.$nextTick((function(){t.previousActiveElement=document.activeElement,t.$refs.content.focus(),t.bind()}))}))},bind:function(){window.addEventListener("focusin",this.onFocusin)},unbind:function(){window.removeEventListener("focusin",this.onFocusin)},onClickOutside:function(t){this.$emit("click:outside",t),this.persistent?this.noClickAnimation||this.animateClick():this.isActive=!1},onKeydown:function(t){if(t.keyCode===g["x"].esc&&!this.getOpenDependents().length)if(this.persistent)this.noClickAnimation||this.animateClick();else{this.isActive=!1;var e=this.getActivator();this.$nextTick((function(){return e&&e.focus()}))}this.$emit("keydown",t)},onFocusin:function(t){if(t&&this.retainFocus){var e=t.target;if(e&&![document,this.$refs.content].includes(e)&&!this.$refs.content.contains(e)&&this.activeZIndex>=this.getMaxZIndex()&&!this.getOpenDependentElements().some((function(t){return t.contains(e)}))){var i=this.$refs.content.querySelectorAll('button, [href], input, select, textarea, [tabindex]:not([tabindex="-1"])'),a=Object(n["a"])(i).find((function(t){return!t.hasAttribute("disabled")}));a&&a.focus()}}},genContent:function(){var t=this;return this.showLazyContent((function(){return[t.$createElement(o["a"],{props:{root:!0,light:t.light,dark:t.dark}},[t.$createElement("div",{class:t.contentClasses,attrs:Object(a["a"])({role:"document",tabindex:t.isActive?0:void 0},t.getScopeIdAttrs()),on:{keydown:t.onKeydown},style:{zIndex:t.activeZIndex},ref:"content"},[t.genTransition()])])]}))},genTransition:function(){var t=this.genInnerContent();return this.transition?this.$createElement("transition",{props:{name:this.transition,origin:this.origin,appear:!0}},[t]):t},genInnerContent:function(){var t={class:this.classes,ref:"dialog",directives:[{name:"click-outside",value:{handler:this.onClickOutside,closeConditional:this.closeConditional,include:this.getOpenDependentElements}},{name:"show",value:this.isActive}],style:{transformOrigin:this.origin}};return this.fullscreen||(t.style=Object(a["a"])(Object(a["a"])({},t.style),{},{maxWidth:"none"===this.maxWidth?void 0:Object(g["g"])(this.maxWidth),width:"auto"===this.width?void 0:Object(g["g"])(this.width)})),this.$createElement("div",t,this.getContentSlot())}},render:function(t){return t("div",{staticClass:"v-dialog__container",class:{"v-dialog__container--attached":""===this.attach||!0===this.attach||"attach"===this.attach},attrs:{role:"dialog"}},[this.genActivator(),this.genContent()])}})},"2b73":function(t,e,i){"use strict";i("f8d9")},"368e":function(t,e,i){},f8d9:function(t,e,i){}}]);
//# sourceMappingURL=chunk-5cca910c.a60d9f05.js.map