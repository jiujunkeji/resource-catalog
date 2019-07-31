$(function () {
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

var vm = new Vue({
	el:'#rrapp',
	data:{
		q: {
            name:null
		},
		showList: true,
        input:'',
		title: null,
		resourceCatalog: {},
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.resourceCatalog = {};
		},
		update: function (event) {
			var catalogId = getCatalogId();
			if(catalogId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(catalogId)
		},
		saveOrUpdate: function (event) {
            if(vm.validator()){
                return ;
            }
			var url = vm.resourceCatalog.catalogId == null ? "resource/resourcecatalog/save" : "resource/resourcecatalog/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.resourceCatalog),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var catalogIds = getCatalogId();
            console.log(catalogIds);
			if(catalogIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "resource/resourcecatalog/delete",
                    contentType: "application/json",
				    data: JSON.stringify(catalogIds),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},

		getInfo: function(catalogId){
			$.get(baseURL + "resource/resourcecatalog/info/"+catalogId, function(r){
                vm.resourceCatalog = r.resourceCatalog;
            });
		},
		reload: function (event) {
			vm.showList = true;
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
	},
	created:function () {

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
        {title: '目录名称', field: 'name', visible: false, align: 'center', valign: 'middle', width: '80px'},
        {title: '目录类型', field: 'type', align: 'center', valign: 'middle', sortable: true, width: '180px'},
        {title: '描述', field: 'remark', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '修改时间', field: 'updateTime', align: 'center', valign: 'middle', sortable: true, width: '80px',},
        {title: '操作', field: 'isUsed', align: 'center', valign: 'middle', sortable: true, width: '100px', formatter: function(item, index){
            if(item.isUsed == 0){
                return '<span class="stop">停用</span>';
            }
            if(item.isUsed == 1){
                return '<span class="ok">使用</span>';
            }
        }}]
    return columns;
};


function getCatalogId () {
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
    var table = new TreeTable(Menu.id, baseURL + "resource/resourcecatalog/list", colunms);
    table.setExpandColumn(1);
    table.setIdField("catalogId");
    table.setCodeField("catalogId");
    table.setParentCodeField("parentId");
    table.setExpandAll(false);
    table.init();
    Menu.table = table;
});