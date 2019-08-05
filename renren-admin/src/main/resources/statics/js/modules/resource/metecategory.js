$(function () {

});
var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "meteCategoryId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    }
};
var ztree;

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		meteCategory: {
            parentId:null,
            parentName:''
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
        getMenu: function(menuId){
            //加载菜单树
            $.get(baseURL + "resource/metecategory/list", function(r){
                console.log(r);
                r.push({
                    parentId:-1,
                    meteCategoryId:0,
                    name:'一级目录'
                })
                ztree = $.fn.zTree.init($("#menuTree"), setting, r);
                var node = ztree.getNodeByParam("meteCategoryId", vm.meteCategory.parentId);
                ztree.selectNode(node);
                console.log(node);
                // vm.menu.parentName = node.name;
            })
        },
        menuTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择菜单",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#menuLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    console.log(node);
                    //选择上级菜单
                    vm.meteCategory.parentId = node[0].meteCategoryId;
                    vm.meteCategory.parentName = node[0].name;
                    layer.close(index);
                }
            });
        },
		add: function(){
            layer.open({
                type: 1,
                title: '新增',
                content: $('#addUp'), //这里content是一个普通的String
                skin: 'openClass',
                area: ['562px', '460px'],
                shadeClose: true,
                closeBtn:0,
                btn: ['新增','取消'],
                btn1:function (index) {
                    vm.saveOrUpdate();

                    layer.close(index);
                },
                btn2:function () {
                    vm.reload();
                }

            })
			vm.showList = false;
			vm.title = "新增";
			vm.meteCategory = {
                parentId:null,
                parentName:''
			};
            vm.getMenu();
		},
		update: function (event) {
			var meteCategoryId = getMeteCategoryId();
			if(meteCategoryId == null){
				return ;
			}
            layer.open({
                type: 1,
                title: '新增',
                content: $('#addUp'), //这里content是一个普通的String
                skin: 'openClass',
                area: ['562px', '460px'],
                shadeClose: true,
                closeBtn:0,
                btn: ['修改','取消'],
                btn1:function (index) {
                    vm.saveOrUpdate();
                    layer.close(index);
                },
                btn2:function () {
                    vm.reload();
                }

            })
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(meteCategoryId);
            vm.getMenu();
		},
		saveOrUpdate: function (event) {
            if(vm.validator()){
                return ;
            }
			var url = vm.meteCategory.meteCategoryId == null ? "resource/metecategory/save" : "resource/metecategory/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.meteCategory),
			    success: function(r){
			    	if(r.code === 0){
                        vm.reload();
                        layer.msg('<div style="color: #3b3b3b;font-size: 18px;text-align: center;padding-top: 50px;line-height: 40px;"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px'],});
					}else{
                        layer.msg('<div style="color: #3b3b3b;font-size: 18px;text-align: center;padding-top: 50px;line-height: 40px;"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
					}
				}
			});
		},
		del: function (event) {
			var meteCategoryIds = getMeteCategoryId();
			console.log(meteCategoryIds);
			if(meteCategoryIds == null){
				return ;
			}

            layer.confirm('确定要删除选中的记录？', function(index1){
				$.ajax({
					type: "POST",
				    url: baseURL + "resource/metecategory/delete",
                    contentType: "application/json",
				    data: JSON.stringify(meteCategoryIds),
				    success: function(r){
						if(r.code == 0){
                            vm.reload();
                            layer.close(index1);
                            layer.msg('<div style="color: #3b3b3b;font-size: 18px;text-align: center;padding-top: 50px;line-height: 40px;"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});


						}else{
                            layer.msg('<div style="color: #3b3b3b;font-size: 18px;text-align: center;padding-top: 50px;line-height: 40px;"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
						}
					}
				});
			});
		},
		getInfo: function(meteCategoryId){
			$.get(baseURL + "resource/metecategory/info/"+meteCategoryId, function(r){
                vm.meteCategory = r.meteCategory;
                console.log('修改')
                console.log(vm.meteCategory)
            });
		},
		reload: function (event) {
			vm.showList = true;
            Menu.table.refresh();
		},
        validator: function () {
            if(isBlank(vm.meteCategory.name)){
                alert("目录名称不能为空");
                return true;
            }
        },
	},
	created:function () {
        // $.get(baseURL + "resource/metecategory/list", function(r){
        //     console.log(r);
        // });
    }
});


var Menu = {
    id: "menuTable",
    table: null,
    layerIndex: -1
};
/**
 * 初始化表格的列
 */
Menu.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '分类名称', field: 'name', visible: false, align: 'center', valign: 'middle', width: '80px'},
        {title: '分类类型', field: 'categoryType', align: 'center', valign: 'middle', sortable: true, width: '180px'},
        {title: '描述', field: 'remark', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '分类代码', field: 'code', align: 'center', valign: 'middle', sortable: true, width: '80px',}]
    return columns;
};


function getMeteCategoryId () {
    var selected = $('#menuTable').bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        alert("请选择一条记录");
        return null;
    } else {
        return selected[0].id;
    }
}


$(function () {
    var colunms = Menu.initColumn();
    var table = new TreeTable(Menu.id, baseURL + "resource/metecategory/list", colunms);
    table.setExpandColumn(1);
    table.setIdField("meteCategoryId");
    table.setCodeField("meteCategoryId");
    table.setParentCodeField("parentId");
    table.setExpandAll(false);
    table.init();
    Menu.table = table;

})