$(function () {
    var _height = $('.divBox').eq(0).find('.switchIn').height();
    var height = _height + 45 + 70;
    vm.h = height;
    // $("#jqGrid").jqGrid({
    //     url: baseURL + 'resource/resourcecatalog/list',
    //     datatype: "json",
    //     colModel: [
		// 	{ label: 'catalogId', name: 'catalogId', index: 'catalog_id', width: 50, key: true },
		// 	{ label: '目录名称', name: 'name', index: 'name', width: 80 },
		// 	{ label: '上级id', name: 'parentId', index: 'parent_id', width: 80 },
		// 	{ label: '目录类型', name: 'type', index: 'type', width: 80 },
		// 	{ label: '描述', name: 'remark', index: 'remark', width: 80 },
		// 	{ label: '创建人id', name: 'createUserId', index: 'create_user_id', width: 80 },
		// 	{ label: '创建人', name: 'createUserName', index: 'create_user_name', width: 80 },
		// 	{ label: '创建部门id', name: 'createDeptId', index: 'create_dept_id', width: 80 },
		// 	{ label: '创建部门', name: 'createDeptName', index: 'create_dept_name', width: 80 },
		// 	{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 },
		// 	{ label: '修改时间', name: 'updateTime', index: 'update_time', width: 80 },
		// 	{ label: '是否使用（0：停用；1：使用）', name: 'isUsed', index: 'is_used', width: 80 },
		// 	{ label: '是否删除（0：未删除；1：已删除）', name: 'isDeleted', index: 'is_deleted', width: 80 }
    //     ],
		// viewrecords: true,
    //     height: 385,
    //     rowNum: 10,
		// rowList : [10,30,50],
    //     rownumbers: true,
    //     rownumWidth: 25,
    //     autowidth:true,
    //     multiselect: true,
    //     pager: "#jqGridPager",
    //     jsonReader : {
    //         root: "page.list",
    //         page: "page.currPage",
    //         total: "page.totalPage",
    //         records: "page.totalCount"
    //     },
    //     prmNames : {
    //         page:"page",
    //         rows:"limit",
    //         order: "order"
    //     },
    //     gridComplete:function(){
    //     	//隐藏grid底部滚动条
    //     	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
    //     }
    // });
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
		q: {
            name:''
		},
		showList: true,
		title: null,
		resourceCatalog: {
            parentId:null,
            parentName:''
        },
        gatntObj: {
            deptId:null,
            userId:null,
        },
        imageUrl:'',
        fileData:null,
        name:null,
        open:true,
        openText:'展开筛选',
        h:0,
        dept:[
            {
                deptId:1,
                deptName:'11',
                childrenList1:null
            }
        ],
        user:[],
        dept:[],
        // props: {
		 //    multiple: true,
        //     value:'deptId',
        //     label:'deptName',
        //     children:'childrenList'
        // },
        propsOut: {
            label: 'name',
            children: 'list',
        },
        menuListOut:[],
        menuList:[],
        props: {
            label: 'name',
            children: 'list',
        },
        // catalogList:
	},
	methods: {
		query: function () {
			vm.reload();
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
                var node = ztree.getNodeByParam("meteCategoryId", vm.resourceCatalog.parentId);
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
                    console.log(node);
                    //选择上级菜单
                    vm.resourceCatalog.parentId = node[0].meteCategoryId;
                    vm.resourceCatalog.parentName = node[0].name;
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
                area: ['562px', '520px'],
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
			vm.resourceCatalog = {
                parentId:null,
                parentName:''
            };
            vm.getMenu();
		},
		update: function () {
			var catalogId = getMeteCategoryId();
			if(catalogId == null){
				return ;
			}
            layer.open({
                type: 1,
                title: '修改',
                content: $('#addUp'), //这里content是一个普通的String
                skin: 'openClass',
                area: ['562px', '520px'],
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
            
            vm.getInfo(catalogId);
            vm.getMenu();
		},
		saveOrUpdate: function (event) {
            if(vm.validator()){
                return ;
            }
			var url = vm.resourceCatalog.catalogId == null ? "resource/metecategory/save" : "resource/metecategory/update";
            const loading = this.$loading({
                lock: true,
                text: 'Loading',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
            });
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.resourceCatalog),
			    success: function(r){
			    	if(r.code === 0){
                        vm.reload();
                        loading.close();
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});
					}else{
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
					}
				}
			});
		},
		del: function () {
			var catalogIds = getMeteCategoryId();
            console.log(catalogIds);
			if(catalogIds == null){
				return ;
			}
			var that = this;
			layer.confirm('确定要删除选中的记录？', function(index){
                const loading = that.$loading({
                    lock: true,
                    text: 'Loading',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });
				$.ajax({
					type: "POST",
				    url: baseURL + "resource/metecategory/delete",
                    contentType: "application/json",
				    data: JSON.stringify(catalogIds),
				    success: function(r){
						if(r.code == 0){
                            layer.close(index);
                            vm.reload();
                            loading.close();
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});

						}else{
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
						}
					}
				});
			});
		},
        clean:function () {
            vm.q.name = null
        },
		getInfo: function(catalogId){
            $.get(baseURL + "resource/metecategory/info/"+catalogId, function(r){
                vm.resourceCatalog = r.meteCategory;
                console.log('修改');
                console.log(vm.resourceCatalog);
            });
		},
		reload: function (event) {
			vm.showList = true;
            Menu.table.setData(vm.q);
            Menu.table.refresh();
		},
        validator: function () {
            if(isBlank(vm.resourceCatalog.name)){
                alert("目录名称不能为空");
                return true;
            }
        },
		downTemplate: function () {
			window.location.href = baseURL + "resource/resourcecatalog/downTemplate"
        },
		// 导出目录
        downCatalog: function () {
            window.location.href = baseURL + "resource/resourcecatalog/downCatalog"
        },
		// 导入
		daoru:function () {

        },
        // 导入完成
        handleAvatarSuccess:function(res, file) {
            // vm.imageUrl = URL.createObjectURL(file.raw);
            // vm.file = file;
            console.log(res);
            if(res.code == 0){
                this.$message({
                    type: 'success',
                    message: '导入成功！'
                });
                vm.reload();
            }
        },
        // 导入前
        beforeAvatarUpload:function(file) {
            var FileExt = file.name.replace(/.+\./, "");
            if (['xlsx','xls'].indexOf(FileExt.toLowerCase()) === -1){
                this.$message({
                    type: 'warning',
                    message: '上传文件只能是excel！'
                });
                return false;
            }else {
            	file.type = 'xls';
            	vm.fileData = file;
            	console.log(vm.fileData);
			}


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
        // 机构
        getDept:function () {
            $.get(baseURL + "resource/organisationinfo/selectList", function(r){
                console.log(r);
                vm.dept = r;
            })
        },
        deptChange:function (obj) {
            console.log(obj);
            vm.gatntObj.deptId = obj;
            var treeF = this.$refs.tree1;
            this.$refs.tree1.setCheckedKeys([]);
            $.ajax({
                type: "GET",
                url: baseURL + "resource/resourcecatalog/selectOgGrant",
                data: {
                    organisationId:vm.gatntObj.deptId
                },
                success: function(r){
                    if(r.code == 0){
                        console.log(r);
                        // vm.gatntObj.deptId = r.deptList;
                        r.catalogIdList.forEach(function (item) {
                            treeF.setChecked(item,true,false);
                        })

                    }else{
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }
                }
            });
            // this.$refs.tree1.setCheckedKeys([]);
            // var idList = [1,5,7,8,9];


        },
        // 用户
        getUser:function () {
            $.get(baseURL + "sys/user/selectList", function(r){
                console.log(r);
                vm.user = r;
            })
        },
        userChange:function (obj) {
            console.log(obj);
            vm.gatntObj.userId = obj;
            var treeF = this.$refs.tree;
            this.$refs.tree.setCheckedKeys([]);
            $.ajax({
                type: "GET",
                url: baseURL + "resource/resourcecatalog/selectGrant",
                data: {
                    userId:vm.gatntObj.userId
                },
                success: function(r){
                    if(r.code == 0){
                        // vm.gatntObj.deptId = r.deptList;
                        r.catalogIdList.forEach(function (item) {
                            treeF.setChecked(item,true,false);
                        })

                    }else{
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }
                }
            });
            this.$refs.tree.setCheckedKeys([]);
            // var idList = [1,5,7,8,9];


        },
        // 授权
        setGrant:function (id) {
            var index = layer.load(2);
            $.ajax({
                type: "POST",
                url: baseURL + "resource/resourcecatalog/grant",
                contentType: "application/json",
                data: JSON.stringify({
                    userId:vm.gatntObj.userId,
                    catalogIdList:id
                }),
                success: function(r){
                    if(r.code == 0){
                        vm.reload();
                        layer.close(index);
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }else{
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }
                }
            });
        },
        getGrant:function () {
		    var treeF = this.$refs.tree;
            layer.open({
                type: 1,
                title: '授权',
                content: $('#grant'), //这里content是一个普通的String
                skin: 'openClass',
                area: ['562px', '520px'],
                shadeClose: true,
                closeBtn:0,
                btn: ['确定','取消'],
                btn1:function (index) {
                    console.log(treeF.getCheckedKeys());
                    console.log(treeF.getHalfCheckedKeys());
                    var allCheck = treeF.getHalfCheckedKeys().concat(treeF.getCheckedKeys());
                    console.log(allCheck);
                    vm.setGrant(allCheck);
                    treeF.setCheckedKeys([]);
                    vm.gatntObj.userId = null;
                    layer.close(index);
                },
                btn2:function () {
                    treeF.setCheckedKeys([]);
                    vm.gatntObj.userId = null;
                    vm.reload();

                }
            })

        },
        // 外部授权
        setOutGrant:function (id) {
            var index = layer.load(2);
            $.ajax({
                type: "POST",
                url: baseURL + "resource/resourcecatalog/grantOg",
                contentType: "application/json",
                data: JSON.stringify({
                    organisationId:vm.gatntObj.deptId,
                    catalogIdList:id
                }),
                success: function(r){
                    if(r.code == 0){
                        vm.reload();
                        layer.close(index);
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }else{
                        layer.close(index);
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }
                }
            });
        },
        getOutGrant:function () {
		    vm.menuListOut = vm.menuList;
            var treeF = this.$refs.tree1;
            layer.open({
                type: 1,
                title: '授权',
                content: $('#grantOut'), //这里content是一个普通的String
                skin: 'openClass',
                area: ['562px', '520px'],
                shadeClose: true,
                closeBtn:0,
                btn: ['确定','取消'],
                btn1:function (index) {
                    console.log(treeF.getCheckedKeys());
                    console.log(treeF.getHalfCheckedKeys());
                    var allCheck = treeF.getHalfCheckedKeys().concat(treeF.getCheckedKeys());
                    console.log(allCheck);
                    vm.setOutGrant(allCheck);
                    treeF.setCheckedKeys([]);
                    vm.gatntObj.deptId = null;
                    layer.close(index);
                },
                btn2:function () {
                    treeF.setCheckedKeys([]);
                    vm.gatntObj.userId = null;
                    vm.reload();

                }
            })

        },
	},
	created:function () {
        // this.h = height
        // this.getDept();
        // this.getUser();
        // this.getDept();
        // this.getMenuList();
        // this.getUser();
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
        {title: '分类名称', field: 'name', visible: false, align: 'center', valign: 'middle', width: '260px'},
        {title: '分类类型', field: 'categoryType', align: 'center', valign: 'middle', sortable: true, width: '180px'},
        {title: '描述', field: 'remark', align: 'center', valign: 'middle', sortable: true, width: ''},
        {title: '分类代码', field: 'code', align: 'center', valign: 'middle', sortable: true, width: '100px',}]
    return columns;
};


function getMeteCategoryId () {
    var selected = $('#menuTable').bootstrapTreeTable('getSelections');
    console.log(selected);
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

// var Menu = {
//     id: "menuTable",
//     table: null,
//     layerIndex: -1
// };
//
// /**
//  * 初始化表格的列
//  */
// Menu.initColumn = function () {
//     var columns = [
//         // {field: 'selectItem', radio: true},
//         {title: '目录名称', field: 'name', visible: false, align: 'center', valign: 'middle', width: '260px'},
//         // {title: '目录类型', field: 'type', align: 'center', valign: 'middle', sortable: true, width: '180px',formatter: function(item, index){
//         //     if(item.type == 0){
//         //         return '资源';
//         //     }
//         //     if(item.isUsed == 1){
//         //         return '服务';
//         //     }
//         // }},
//         {title: '描述', field: 'remark', align: 'center', valign: 'middle', sortable: true, width: ''},
//         {title: '修改时间', field: 'updateTime', align: 'center', valign: 'middle', sortable: true, width: '200px',},
//         {title: '使用情况', field: 'isUsed', align: 'center', valign: 'middle', sortable: true, width: '100px', formatter: function(item, index){
//         	if(item.isUsed == 0){
//                 return '<div style="margin-left: 6px" class="layui-unselect layui-form-switch" onClick="ss('+item.isUsed+','+item.catalogId+')"><em>停用</em><i></i></div>';
// 			}
// 			if(item.isUsed == 1){
//                 return '<div style="margin-left: 6px" class="layui-unselect layui-form-switch layui-form-onswitch" onClick="ss('+item.isUsed+','+item.catalogId+')"><em>使用</em><i></i></div>';
// 			}
//         }},
//         {title: '操作', field: '', align: 'left', valign: 'middle', sortable: true, width: '100px', formatter: function(item, index){
//             return '<i class="el-icon-edit" style="margin-left: 10px" onclick="updateT('+item.catalogId+')"></i> <span style="color: #ccc;display: inline-block;margin: 0 6px">|</span> <i class="el-icon-delete" onclick="delT('+item.catalogId+')"></i>'
//         }}]
//     return columns;
// };
//
// function ss(num,id) {
// 	if(num == 0){
//         layer.confirm('确定使用吗？',function (index1) {
//             $.get(baseURL + "resource/resourcecatalog/start?catalogId="+id, function(r){
//                 Menu.table.refresh();
//                 layer.close(index1)
//             });
// 		})
//
// 	}else {
//         layer.confirm('确定停用吗？',function (index1) {
//             $.get(baseURL + "resource/resourcecatalog/stop?catalogId="+id, function(r){
//                 Menu.table.refresh();
//                 layer.close(index1)
//             });
//         })
// 	}
// }
// function getCatalogId () {
//     var selected = $('#menuTable').bootstrapTreeTable('getSelections');
//     if (selected.length == 0) {
//         alert("请选择一条记录");
//         return null;
//     } else {
//         return selected[0].id;
//     }
// }
//
// function updateT(id) {
//     vm.update(id);
// }
// function delT(id) {
//     vm.del(id);
// }
//
//
// $(function () {
//     var colunms = Menu.initColumn();
//     var table = new TreeTable(Menu.id, baseURL + "xj/xjcatalog/list", colunms);
//     table.setExpandColumn(0);
//     table.setIdField("catalogId");
//     table.setCodeField("catalogId");
//     table.setParentCodeField("parentId");
//     table.setExpandAll(false);
//     table.setData(vm.q);
//     table.init();
//     Menu.table = table;
//
//
// });