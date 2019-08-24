//生成菜单
var menuItem = Vue.extend({
    name: 'menu-item',
    props:{item:{}},
    template:[
        '<li>',
        '	<a v-if="item.type === 0" href="javascript:;">',
        '		<i v-if="item.icon != null" :class="item.icon"></i>',
        '		<span>{{item.name}}</span>',
        '		<i class="fa fa-angle-right pull-right"></i>',
        '	</a>',
        '	<ul v-if="item.type === 0" class="treeview-menu">',
        '		<menu-item :item="item" v-for="item in item.list"></menu-item>',
        '	</ul>',

        '	<a v-if="item.type === 1 && item.parentId === 0" :href="\'#\'+item.url">',
        '		<i v-if="item.icon != null" :class="item.icon"></i>',
        '		<span>{{item.name}}</span>',
        '	</a>',

        '	<a v-if="item.type === 1 && item.parentId != 0" :href="\'#\'+item.url" @click="toOpen(item)"> {{item.name}}</a>',
        '</li>'
    ].join('')
});
// <i v-if="item.icon != null" :class="item.icon"></i><i v-else class="fa fa-circle-o"></i>

//iframe自适应
$(window).on('resize', function() {
	var $content = $('.content');
	$content.height($(this).height() - 76);
	$content.find('iframe').each(function() {
		$(this).height($content.height());
	});
}).resize();

//注册菜单组件
Vue.component('menuItem',menuItem);

var vm = new Vue({
	el:'#homepage',
	data:{
		user:{},
		menuList:{},
		main:"modules/resource/resources.html",
		password:'',
		newPassword:'',
        navTitle:"控制台",
        topMenuList:[],
		tab:0,
        pushItemMenu:{},
        history:[],
	},
	methods: {
		getMenuList: function (event) {
			$.getJSON("sys/menu/nav?_"+$.now(), function(r){
				console.log(r);
				vm.menuList = r.menuList;
			});
		},
		getUser: function(){
			$.getJSON("sys/user/info?_"+$.now(), function(r){
				vm.user = r.user;
			});
		},
		updatePassword: function(){
			layer.open({
				type: 1,
				skin: 'layui-layer-molv',
				title: "修改密码",
				area: ['550px', '270px'],
				shadeClose: false,
				content: jQuery("#passwordLayer"),
				btn: ['修改','取消'],
				btn1: function (index) {
					var data = "password="+vm.password+"&newPassword="+vm.newPassword;
					$.ajax({
						type: "POST",
					    url: "sys/user/password",
					    data: data,
					    dataType: "json",
					    success: function(result){
							if(result.code == 0){
								layer.close(index);
								layer.alert('修改成功', function(index){
									location.reload();
								});
							}else{
								layer.alert(result.msg);
							}
						}
					});
	            }
			});
		},
        donate: function () {
            layer.open({
                type: 2,
                title: false,
                area: ['806px', '467px'],
                closeBtn: 1,
                shadeClose: false,
                content: ['http://cdn.renren.io/donate.jpg', 'no']
            });
        },
        // 点击顶部菜单方法
		topMenuListClick:function (item) {
            toOpen(item);
        },
        // 回首页
        openHome:function () {
            vm.main = 'modules/resource/resources.html';
            //导航菜单展开
            $(".sidebar-menu li").removeClass("active");
            vm.tab = 0;
        },
        delTopMenu:function (index) {
            del();
		    if(index == 0){
                vm.topMenuList.splice(index,1);
                vm.tab = 0;
                vm.main = 'modules/resource/resources.html';
                vm.history.splice(index,1);
                console.log(vm.topMenuList);
                console.log(vm.history);
            }else {
                vm.history.splice(index,1);
                vm.topMenuList.splice(index,1);
                vm.tab = index;
                vm.main = vm.topMenuList[index-1].url;

                // vm.history.splice(index,1);
                console.log(vm.history);
                console.log(vm.topMenuList);
            }

        },
        toOpen:function (item) {
			console.log(item)
        }
	},
	created: function(){
		this.getMenuList();
		this.getUser();
        var router = new Router();
        routerList(router, this.menuList);
        router.start();
	},
	updated: function(){
		//路由

		// console.log(router);

	}
});



function routerList(router, menuList){
	for(var key in menuList){
		var menu = menuList[key];
		if(menu.type == 0){
			routerList(router, menu.list);
		}else if(menu.type == 1){
			router.add('#'+menu.url, function() {
				var url = window.location.hash;
				//替换iframe的url
			    vm.main = url.replace('#', '');
			    //导航菜单展开
			    $(".treeview-menu li").removeClass("active");
			    $("a[href='"+url+"']").parents("li").addClass("active");
			    
			    vm.navTitle = $("a[href='"+url+"']").text();
                vm.pushItemMenu = menu;
                if(vm.history.indexOf(menu.menuId) == -1){
                    vm.history.push(menu.menuId)
                    vm.topMenuList[vm.topMenuList.length] = {
                        icon :  vm.pushItemMenu.icon,
                        list: vm.pushItemMenu.list,
                        menuId: vm.pushItemMenu.menuId,
                        name: vm.pushItemMenu.name,
                        open: vm.pushItemMenu.open,
                        orderNum: vm.pushItemMenu.orderNum,
                        parentId: vm.pushItemMenu.parentId,
                        parentName: vm.pushItemMenu.parentName,
                        perms: vm.pushItemMenu.perms,
                        type: vm.pushItemMenu.type,
                        url: vm.pushItemMenu.url,
                    };
                    vm.tab = vm.topMenuList.length ;
                    console.log(vm.topMenuList.length);
                    console.log(vm.tab)
                }else {
                    vm.topMenuList.forEach(function (t,i) {
                        if(t.menuId == menu.menuId){
                            vm.tab = i+1;
                        }
                    })
                }

			});

		}
	}
	// console.log('111111');

}

function routerList1(router, menuItem){
    var menu = menuItem;
    if(menu.type == 0){
        routerList1(router, menu.list);
    }else if(menu.type == 1){
        router.add('#'+menu.url, function() {
            var url = window.location.hash;

            //替换iframe的url
            vm.main = url.replace('#', '');
            // vm.topMenuList.push(menu);

            //导航菜单展开
            $(".treeview-menu li").removeClass("active");
            $("a[href='"+url+"']").parents("li").addClass("active");

            vm.navTitle = $("a[href='"+url+"']").text();


            console.log(vm.topMenuList);

        });
    }
}
function toOpen(item) {
    vm.main = item.url;
    //导航菜单展开
    $(".sidebar-menu li").removeClass("active");
    $("a[href='#"+item.url+"']").parents("li").addClass("active");
    // $("a[href='#"+item.url+"']").parents("li").parent('ul').addClass("menu-open");
    vm.navTitle = $("a[href='#"+item.url+"']").text();
    vm.pushItemMenu = item;
    if(vm.history.indexOf(item.menuId) == -1){
        vm.history.push(item.menuId);
        vm.topMenuList[vm.topMenuList.length] = {
            icon :  vm.pushItemMenu.icon,
            list: vm.pushItemMenu.list,
            menuId: vm.pushItemMenu.menuId,
            name: vm.pushItemMenu.name,
            open: vm.pushItemMenu.open,
            orderNum: vm.pushItemMenu.orderNum,
            parentId: vm.pushItemMenu.parentId,
            parentName: vm.pushItemMenu.parentName,
            perms: vm.pushItemMenu.perms,
            type: vm.pushItemMenu.type,
            url: vm.pushItemMenu.url,
        };
        vm.tab = vm.topMenuList.length ;
        console.log(vm.topMenuList.length);
        console.log(vm.tab)
    }else {
        vm.topMenuList.forEach(function (t,i) {
            if(t.menuId == item.menuId){
                vm.tab = i+1;
            }
        })
    }

}
function del() {
    $(".sidebar-menu li").removeClass("active");
}
