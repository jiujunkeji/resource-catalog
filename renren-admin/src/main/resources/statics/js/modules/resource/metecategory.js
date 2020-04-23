$(function () {
    var _height = $('.divBox').eq(0).find('.switchIn').height();
    var height = _height + 45 + 70;
    vm.h = height;
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
        },

    }
};
var ztree;

var vm = new Vue({
	el:'#rrapp',
	data:{
        q: {
            name:null
        },
		showList: true,
		title: null,
		meteCategory: {
            parentId:null,
            parentName:''
		},
        tableList:[],
        totalPage:0,
        page:1,
        checkIdList:[],
        open:true,
        openText:'展开筛选',
        h:0,
	},
	methods: {
		query: function () {
			vm.reload();
		},
        // 收缩展开搜索
        openSwitch:function () {
            if(vm.open){
                vm.open = false;
                vm.openText = '收起筛选'

            }else {
                vm.open = true;
                vm.openText = '展开筛选'
            }
        },
        clean:function () {
            vm.q.name = null
        },
        // 表格选中方法
        toggleSelection:function(selection) {
            console.log(selection);
            vm.checkIdList = [];
            selection.forEach(function(item,i){
                vm.checkIdList.push(item.meteCategoryId)
            })
            console.log(vm.checkIdList);

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
		},
		saveOrUpdate: function (event) {
            if(vm.validator()){
                return ;
            }
			var url = vm.meteCategory.meteCategoryId == null ? "xj/xjmetecategory/save" : "xj/xjmetecategory/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.meteCategory),
			    success: function(r){
			    	if(r.code === 0){
                        vm.reload();
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px'],});
					}else{
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
					}
				}
			});
		},
		del: function (event) {


            layer.confirm('确定要删除选中的记录？', function(index1){
				$.ajax({
					type: "POST",
				    url: baseURL + "xj/xjmetecategory/delete",
                    contentType: "application/json",
				    data: JSON.stringify(vm.checkIdList),
				    success: function(r){
						if(r.code == 0){
                            vm.reload();
                            layer.close(index1);
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});


						}else{
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
						}
					}
				});
			});
		},
		getInfo: function(meteCategoryId){
			$.get(baseURL + "xj/xjmetecategory/info/"+meteCategoryId, function(r){
                vm.meteCategory = r.meteCategory;
                console.log('修改')
                console.log(vm.meteCategory)
            });
		},
		reload: function (event) {
			vm.showList = true;
            // Menu.table.refresh();
            vm.getTableList();
		},
        validator: function () {
            if(isBlank(vm.meteCategory.name)){
                alert("目录名称不能为空");
                return true;
            }
        },
        // 获取表格列表
        getTableList:function () {
            $.ajax({
                type: "get",
                url: baseURL + 'xj/xjmetecategory/queryList',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    page:this.page,
                    params:this.q.name
                },
                success: function(r){
                    console.log(r);
                    // vm.tableList = r;
                    if(r.code === 0){
                        vm.tableList = r.page.list;
                        vm.totalPage = r.page.totalCount;
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        // 分页
        layerPage:function (currentPage) {
            console.log(currentPage);
            vm.page = currentPage;
            vm.getTableList();
        },
        //启用
        open:function () {
            $.ajax({
                type: "get",
                url: baseURL + 'xj/xjmetecategory/updateEnabledState',
                // contentType: "application/json",
                dataType: 'json',
                data: JSON.stringify(vm.checkIdList),
                success: function(r){
                    console.log(r);
                }
            });
        },
        // 禁用
        close:function () {
            $.ajax({
                type: "get",
                url: baseURL + 'xj/xjmetecategory/updateDisabledState',
                // contentType: "application/json",
                dataType: 'json',
                data: JSON.stringify(vm.checkIdList),
                success: function(r){
                    console.log(r);
                }
            });
        }
	},
	created:function () {
	    this.getTableList();
        // $.get(baseURL + "resource/metecategory/list", function(r){
        //     console.log(r);
        // });
    }
});


// var Menu = {
//     id: "menuTable",
//     table: null,
//     layerIndex: -1
// };
/**
 * 初始化表格的列
 */
// Menu.initColumn = function () {
//     var columns = [
//         {field: 'selectItem', radio: true},
//         {title: '分类名称', field: 'name', visible: false, align: 'center', valign: 'middle', width: '260px'},
//         {title: '分类类型', field: 'categoryType', align: 'center', valign: 'middle', sortable: true, width: '180px'},
//         {title: '描述', field: 'remark', align: 'center', valign: 'middle', sortable: true, width: ''},
//         {title: '分类代码', field: 'code', align: 'center', valign: 'middle', sortable: true, width: '100px',}]
//     return columns;
// };


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
    // var colunms = Menu.initColumn();
    // var table = new TreeTable(Menu.id, baseURL + "resource/metecategory/list", colunms);
    // table.setExpandColumn(1);
    // table.setIdField("meteCategoryId");
    // table.setCodeField("meteCategoryId");
    // table.setParentCodeField("parentId");
    // table.setExpandAll(false);
    // table.init();
    // Menu.table = table;

})