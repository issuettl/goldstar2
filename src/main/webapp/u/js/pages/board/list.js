$(function(){
	//페이징
    $(".pagination").find("button").on({
        "click" : function(){
            var page = $(this).data("page");
            $("#searchForm > input[name='page']").val(page);
            $("#searchForm").submit();
        }
    });
});
