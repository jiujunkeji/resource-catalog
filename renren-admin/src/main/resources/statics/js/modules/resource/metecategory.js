$(function () {
    // $("#jqGrid").jqGrid({
    //     url: baseURL + 'resource/metecategory/list',
    //     datatype: "json",
    //     colModel: [
		// 	{ label: 'meteCategoryId', name: 'meteCategoryId', index: 'mete_category_id', width: 50, key: true },
		// 	{ label: '分类类型', name: 'categoryType', index: 'category_type', width: 80 },
		// 	{ label: '分类名称', name: 'name', index: 'name', width: 80 },
		// 	{ label: '分类代码', name: 'code', index: 'code', width: 80 },
		// 	{ label: '上级id', name: 'parentId', index: 'parent_id', width: 80 },
		// 	{ label: '描述', name: 'remark', index: 'remark', width: 80 }
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
		showList: true,
		title: null,
		meteCategory: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.meteCategory = {};
		},
		update: function (event) {
			var meteCategoryId = getMeteCategoryId();
			if(meteCategoryId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(meteCategoryId)
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
			var meteCategoryIds = getMeteCategoryId();
			console.log(meteCategoryIds);
			if(meteCategoryIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "resource/metecategory/delete",
                    contentType: "application/json",
				    data: JSON.stringify(meteCategoryIds),
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
		getInfo: function(meteCategoryId){
			$.get(baseURL + "resource/metecategory/info/"+meteCategoryId, function(r){
                vm.meteCategory = r.meteCategory;
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