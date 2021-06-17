;(function($){
	$.fn.listTab = function(ops){
		var ops=$.extend({
			on1 : "active",
			on2 : "",
			list : false,
			hasTitle : false
		},ops);
		var o=$(this).children("li");
		var list_Obj = $("#min-menu");
		if(ops.list) {
			o.each(function(){
				var _t = $(this);
				_t.click(function(){
					_t.siblings("li").removeClass(ops.on1).end().addClass(ops.on1);
					list_Obj.children().removeClass(ops.on1).end().children(":eq("+_t.index()+")").addClass(ops.on1);
				});
			});
		} else {
			o.each(function(){
				var _t = $(this);
				
				_t.click(function(){
					if(ops.hasTitle && _t.index()==0) {
						return false;
					}
					_t.siblings("li").removeClass(ops.on1).end().addClass(ops.on1);

				});
			});
		}
	};
	$.fn.maxIndex = function(ops2) {
		var ops2=$.extend({
			active : "active"
		},ops2);
		var items = $(this).children("li");
		var maxList_Obj = $("#max-menu");
		items.each(function(){
			var _t = $(this);
			_t.click(function(){
				maxList_Obj.children().removeClass(ops2.active).end().children(":eq("+_t.index()+")").addClass(ops2.active);
			});
		});
	};
	$.fn.horMenu = function() {
		$(this).find('li').hover(function() {
			$(this).children("ul").show();
		},function() {
			$(this).children("ul").hide();
		}).click(function(){
			$(this).addClass('active').siblings().removeClass('active');
		});
		
	};
})(jQuery);

// 导航菜单 - 方法
function afterPageLoad() {
  $('#pageBody .menu').menu();
  $('#pageBody .menu .nav li:not(".nav-parent") a').click(function() {
      var $this = $(this);
      $('.menu .nav .active').removeClass('active');
      $this.closest('li').addClass('active');
      var parent = $this.closest('.nav-parent');
      if(parent.length) {
          parent.addClass('active');
      }
  });
}

function winAutoH() {
	var topH = $("[name='top']").height(), navBoxH = $("[name='menu-box']").height();
	var winH = (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight)-topH-navBoxH-87;
	$("[data='winH']").height(winH);
}

function loadingCreate() {
	var obj = document.createElement("div");
	var loader = document.createElement("div");
	var objArray = new Array();
	objArray = {
		display         : 'block',
		backgroundColor : 'black',
		filter          : 'progid:DXImageTransform.Microsoft.Alpha(opacity=50)',
		opacity         : 0.5
	};
	obj.className = "modal modal-loading";
	obj.id = 'loadingWait';
	for(var i in objArray) {
		obj.style[i] = objArray[i];
	}
	loader.className = "icon-spinner icon-spin loader";
	obj.appendChild(loader);
	document.getElementsByTagName('body')[0].appendChild(obj);
}

function loadingRemove() {
	document.getElementsByTagName('body')[0].removeChild(document.getElementById('loadingWait'));
}

$(function(){
	$(window).bind("load resize",function(){winAutoH();});
});