(function(){return function(){if(!this._is_form){return;}var _a=null;this.on_create=function(){this.set_name("abc");this.set_titletext("New Form");if(Form==this.constructor){this._setFormPosition(1280,720);}_a=new Calendar("Calendar00","204","80","288","136",null,null,null,null,null,null,this);_a.set_taborder("0");this.addChild(_a.name,_a);_a=new Layout("default","",1280,720,this,function(_b){});_a.set_mobileorientation("landscape");this.addLayout(_a.name,_a);};this.loadPreloadList=function(){};this.on_initEvent=function(){};this.loadIncludeScript("abc.xfdl");this.loadPreloadList();_a=null;};})();