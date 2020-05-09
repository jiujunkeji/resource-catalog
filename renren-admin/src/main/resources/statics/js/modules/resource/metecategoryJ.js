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
            name:'',
            metaCategorySetNumber:''
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
            vm.q = {
                name:'',
                metaCategorySetNumber:''
            };
            vm.getTableList();
        },
        // 表格选中方法
        toggleSelection:function(selection) {
            vm.checkIdList = [];
            selection.forEach(function(item,i){
                vm.checkIdList.push(item.meteCategorySetId)
            })
        },
        getMenu: function(menuId){
            //加载菜单树
            $.get(baseURL + "resource/metecategory/list", function(r){
                // r.push({
                //     parentId:-1,
                //     meteCategoryId:0,
                //     name:'一级目录'
                // })
                ztree = $.fn.zTree.init($("#menuTree"), setting, r);
                var node = ztree.getNodeByParam("meteCategoryId", vm.meteCategory.parentId);
                ztree.selectNode(node);
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
                    //选择上级菜单
                    vm.meteCategory.parentId = node[0].meteCategoryId;
                    vm.meteCategory.parentName = node[0].name;
                    layer.close(index);
                }
            });
        },
		add: function(){
            var that = this;
            layer.open({
                type: 1,
                title: '新增',
                content: $('#addUp'), //这里content是一个普通的String
                skin: 'openClass',
                area: ['562px', '360px'],
                shadeClose: true,
                closeBtn:0,
                btn: ['新增','取消'],
                btn1:function (index) {
                    if(vm.meteCategory.metaCategorySetNumber == '' || vm.meteCategory.name == ''){
                        that.$message({
                            message: "带 ' * ' 的为必填项",
                            type: 'warning'
                        });
                    }else{
                        const loading = that.$loading({
                            lock: true,
                            text: 'Loading',
                            spinner: 'el-icon-loading',
                            background: 'rgba(0, 0, 0, 0.7)'
                        });
                        $.ajax({
                            type: "POST",
                            url: baseURL + 'xj/xjmetesetcategory/save',
                            contentType: "application/json",
                            data: JSON.stringify(vm.meteCategory),
                            success: function(r){
                                if(r.code === 0){
                                    vm.reload();
                                    layer.close(index);
                                    loading.close();
                                    layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px'],});
                                }else{
                                    loading.close();
                                    layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>'+r.msg+'</div>',{skin:'bg-class',area: ['400px', '270px']});
                                }
                            }
                        });
                    }



                },
                btn2:function () {
                    vm.reload();
                }

            })
			vm.showList = false;
			vm.title = "新增";
			vm.meteCategory = {
                metaCategorySetNumber:null,
                name:''
			};
		},
		update: function (id) {
		    var that = this;
            layer.open({
                type: 1,
                title: '修改',
                content: $('#addUp'), //这里content是一个普通的String
                skin: 'openClass',
                area: ['562px', '360px'],
                shadeClose: true,
                closeBtn:0,
                btn: ['修改','取消'],
                btn1:function (index) {
                    if(vm.meteCategory.metaCategorySetNumber == '' || vm.meteCategory.name == ''){
                        that.$message({
                            message: "带 ' * ' 的为必填项",
                            type: 'warning'
                        });
                    }else{
                        const loading = that.$loading({
                            lock: true,
                            text: 'Loading',
                            spinner: 'el-icon-loading',
                            background: 'rgba(0, 0, 0, 0.7)'
                        });
                        $.ajax({
                            type: "POST",
                            url: baseURL + 'xj/xjmetesetcategory/update',
                            contentType: "application/json",
                            data: JSON.stringify(vm.meteCategory),
                            success: function(r){
                                if(r.code === 0){
                                    vm.reload();
                                    layer.close(index);
                                    loading.close();
                                    layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px'],});
                                }else{
                                    loading.close();
                                    layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>'+r.msg+'</div>',{skin:'bg-class',area: ['400px', '270px']});
                                }
                            }
                        });
                    }

                },
                btn2:function () {
                    vm.reload();
                }

            })
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id);
            vm.getMenu();
		},
		saveOrUpdate: function (event) {
            if(vm.validator()){
                return ;
            }
			var url = vm.meteCategory.meteCategorySetId == null ? "xj/xjmetesetcategory/save" : "xj/xjmetesetcategory/update";
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
		    var that = this;
			// var meteCategoryIds = getMeteCategoryId();
            if(vm.checkIdList.length == 0){
                this.$message({
                    message: '请选择一条记录',
                    type: 'warning'
                });
            }else{
                layer.confirm('确定要删除选中的记录？', function(index1){
                    const loading = that.$loading({
                        lock: true,
                        text: 'Loading',
                        spinner: 'el-icon-loading',
                        background: 'rgba(0, 0, 0, 0.7)'
                    });
                    $.ajax({
                        type: "POST",
                        url: baseURL + "xj/xjmetesetcategory/delete",
                        contentType: "application/json",
                        data: JSON.stringify(vm.checkIdList),
                        success: function(r){
                            if(r.code == 0){
                                vm.reload();
                                layer.close(index1);
                                loading.close();
                                layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});


                            }else{
                                loading.close();
                                layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
                            }
                        }
                    });
                });
            }


		},
		getInfo: function(meteCategoryId){
			$.get(baseURL + "xj/xjmetesetcategory/info/"+meteCategoryId, function(r){
                vm.meteCategory = r.xjMeteSetCategory;
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
		    var _this = this;
            $.ajax({
                type: "get",
                url: baseURL + 'xj/xjmetesetcategory/queryList',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    page:this.page,
                    name:this.q.name,
                    metaCategorySetNumber:this.q.metaCategorySetNumber,
                },
                success: function(r){
                    if(r.code === 0){
                        vm.tableList = r.page.list;
                        vm.totalPage = r.page.totalCount;
                        if(vm.tableList.length == 0 && vm.page > 1){
                            vm.page = vm.page-1;
                            vm.getTableList();
                        }
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        // 分页
        layerPage:function (currentPage) {
            vm.page = currentPage;
            vm.getTableList();
        },
        //启用
        openC:function () {
		    var that = this;
            if(vm.checkIdList.length == 0){
                this.$message({
                    message: '请选择一条记录',
                    type: 'warning'
                });
            }else {
                layer.confirm('确定要启动选中的分类？', function(index31){
                    const loading = that.$loading({
                        lock: true,
                        text: 'Loading',
                        spinner: 'el-icon-loading',
                        background: 'rgba(0, 0, 0, 0.7)'
                    });
                    $.ajax({
                        type: "POST",
                        url: baseURL + 'xj/xjmetesetcategory/updateEnabledState',
                        contentType: "application/json",
                        // dataType: 'json',
                        data: JSON.stringify(vm.checkIdList),
                        success: function(r){
                            if(r.code == 0){
                                layer.close(index31);
                                loading.close();
                                layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});
                                vm.getTableList();
                            }else {
                                layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
                            }

                        }
                    });
                })
            }


        },
        // 禁用
        closeC:function () {
		    var that = this;
            if(vm.checkIdList.length == 0){
                this.$message({
                    message: '请选择一条记录',
                    type: 'warning'
                });
            }else {
                layer.confirm('确定要禁用选中的分类？', function(index32){
                    const loading = that.$loading({
                        lock: true,
                        text: 'Loading',
                        spinner: 'el-icon-loading',
                        background: 'rgba(0, 0, 0, 0.7)'
                    });
                    $.ajax({
                        type: "POST",
                        url: baseURL + 'xj/xjmetesetcategory/updateDisabledState',
                        contentType: "application/json",
                        // dataType: 'json',
                        data: JSON.stringify(vm.checkIdList),
                        success: function(r){
                            if(r.code == 0){
                                layer.close(index32);
                                loading.close();
                                layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});
                                vm.getTableList();
                            }else {
                                layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
                            }
                        }
                    });
                })
            }

        }
	},
	created:function () {
	    this.getTableList();
        // $.get(baseURL + "resource/metecategory/list", function(r){
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