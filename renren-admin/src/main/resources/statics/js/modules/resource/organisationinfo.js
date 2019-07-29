$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'resource/organisationinfo/list',
        datatype: "json",
        colModel: [			
			{ label: 'organisationId', name: 'organisationId', index: 'organisation_id', width: 50, key: true },
			{ label: '机构名称', name: 'organisationName', index: 'organisation_name', width: 80 }, 			
			{ label: '机构地址', name: 'organisationAddr', index: 'organisation_addr', width: 80 }, 			
			{ label: '联系人', name: 'linkman', index: 'linkman', width: 80 }, 			
			{ label: '联系电话', name: 'phone', index: 'phone', width: 80 }			
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
		organisationInfo: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.organisationInfo = {};
		},
		update: function (event) {
			var organisationId = getSelectedRow();
			if(organisationId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(organisationId)
		},
		saveOrUpdate: function (event) {
			var url = vm.organisationInfo.organisationId == null ? "resource/organisationinfo/save" : "resource/organisationinfo/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.organisationInfo),
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
			var organisationIds = getSelectedRows();
			if(organisationIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "resource/organisationinfo/delete",
                    contentType: "application/json",
				    data: JSON.stringify(organisationIds),
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
		getInfo: function(organisationId){
			$.get(baseURL + "resource/organisationinfo/info/"+organisationId, function(r){
                vm.organisationInfo = r.organisationInfo;
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