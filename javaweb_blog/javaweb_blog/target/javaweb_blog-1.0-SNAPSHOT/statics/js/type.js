/*
* 打开添加模态框
* */
$("#addBtn").click(function () {
    //设置标题
    $("#myModalLabel").html("新增")

    //清空文本框中的值
    $("#typeId").val('')
    $("#typeName").val('')

    //清空提示信息
    $("#msg").val('')

    //打开模块框
    $("#myModal").modal("show")
})

/*
* 打开修改框
* */
function openUpdateDialog(typeId){

    //设置标题
    $("#myModalLabel").html("修改")

    //通过id选择器 得到 对应的修改数据
    let tr = $("#tr_" + typeId)

    //得到 tr 具体的 单元格的值 （第二个 td）
    let typeName = tr.children().eq(1).text();

    //将 类型名称设置给 修改框 的文本框
    $("#typeName").val(typeName)


    //设置类型id 到 隐藏id中
    $("#typeId").val(typeId)

    //清空提示信息
    $("#msg").val('')

    //打开模块框
    $("#myModal").modal("show")
}

/*
* 添加或者修改类型
* */
$("#btn_submit").click(function () {
    //获取参数
    let typeName = $("#typeName").val()
    let typeId = $("#typeId").val()

    //判断是否为空
    if(isEmpty(typeName)) {
        $("#msg").html("类型名称不能为空！")
        return
    }

    //弹出框
    swal({
        title: "", //标题
        text: "<h3>您确定要修改吗？</h3>", //内容
        type:"warning", //图标
        showCancelButton: "true", //显示取消按钮
        cancelButtonColor: "orange", //取消按钮的颜色
        confirmButtonText: "确定", //确定按钮的文本
        cancelButtonText: "取消" //取消按钮的文本
    }).then(function () {
        //3. 发起请求
        $.ajax({
            type: "post",
            url: "type",
            data: {
                actionName: "addOrUpdate",
                typeName: typeName,
                typeId: typeId
            },
            dataType:"json",
            success: function (result) {
                if (result.code == 200) {

                    //提示用户 删除成功
                    swal(
                        "Success",
                        "<h3>修改成功</h3>",
                        "success"
                    )

                    //关闭模块框
                    $("#myModal").modal("hide")
                    /*                //判断id是否为空
                                    if (isEmpty(typeId)){
                                        addDom(typeName, result.result)
                                    }else {
                                        updateDom(typeName, result.result)
                                    }*/
                    //定时器， 两秒后 刷新页面
                    setTimeout(function (){
                        location.reload()
                    }, 2000)

                }else {
                    //提示用户 删除失败
                    swal(
                        "Error",
                        "<h3>result.msg</h3>",
                        "error"
                    )
                }
            }
        })
    })
})

/*
* 添加类型Dom操作
* */
function addDom(typeName, typeId) {
    var tr = '<td id="tr_'+typeId+'"><td>' + typeId +'</td><td>'+typeName +'</td>';
    tr += '<td><button class="btn btn-primary" type="button" onclick="">修改</button>&nbsp;'
    tr += '<button class="btn btn-danger del" type="button" onclick="">删除</button></td></tr>'

    //获取表格对象
    let myTable = $("#myTable")

    //表格是否存在
    if (myTable.length > 0) {
        //追加
        myTable.append(tr)
    }else {
        myTable = '<table id="myTable" class="table table-hover table-striped">'
        myTable += '<tbody> <tr> <th>编号</th> <th>类型</th> <th>操作</th> </tr>'
        myTable += tr + '</tbody></table>'
        $("#myDiv").html(myTable)
    }

    /* 添加 左侧 类别列表 */
}


/*
* 删除类别
* */
function deleteType(typeId) {
    //弹出框
    swal({
        title: "", //标题
        text: "<h3>您确定要删除吗？</h3>", //内容
        type:"warning", //图标
        showCancelButton: "true", //显示取消按钮
        cancelButtonColor: "orange", //取消按钮的颜色
        confirmButtonText: "确定", //确定按钮的文本
        cancelButtonText: "取消" //取消按钮的文本
    }).then(function () {
        // 发送 ajax 请求
        $.ajax({
            type: "post",
            url: "type",
            data: {
                actionName: "delete",
                typeId: typeId
            },
            dataType: "json",
            success: function (result) {
                //删除成功
                if (result.code == 200) {
                    //提示用户 删除成功
                    swal(
                        "Success",
                        "<h3>删除成功</h3>",
                        "success"
                    )
                    //定时器， 两秒后 刷新页面
                    setTimeout(function (){
                        location.reload()
                    }, 2000)
                }else {
                    //提示用户 删除失败
                    swal(
                        "Error",
                        "<h3>result.msg</h3>",
                        "error"
                    )
                }
            }
        })
    })
}



