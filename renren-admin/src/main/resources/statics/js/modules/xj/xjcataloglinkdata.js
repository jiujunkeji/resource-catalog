$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'xj/xjcataloglinkdata/list',
        datatype: "json",
        colModel: [			
			{ label: 'linkId', name: 'linkId', index: 'link_id', width: 50, key: true },
			{ label: '目录id', name: 'catalogId', index: 'catalog_id', width: 80 }, 			
			{ label: '数据源id', name: 'dataSourceId', index: 'data_source_id', width: 80 }, 			
			{ label: '数据表名', name: 'tableName', index: 'table_name', width: 80 }			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		xjCatalogLinkData: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.xjCatalogLinkData = {};
		},
		update: function (event) {
			var linkId = getSelectedRow();
			if(linkId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(linkId)
		},
		saveOrUpdate: function (event) {
			var url = vm.xjCatalogLinkData.linkId == null ? "xj/xjcataloglinkdata/save" : "xj/xjcataloglinkdata/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.xjCatalogLinkData),
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
			var linkIds = getSelectedRows();
			if(linkIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "xj/xjcataloglinkdata/delete",
                    contentType: "application/json",
				    data: JSON.stringify(linkIds),
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
		getInfo: function(linkId){
			$.get(baseURL + "xj/xjcataloglinkdata/info/"+linkId, function(r){
                vm.xjCatalogLinkData = r.xjCatalogLinkData;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});