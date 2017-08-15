
/**
 * 获取比特币地址余额
 * @param address 比特币钱包地址
 */
function getBitCoinBalance(address){
    if(!address.trim()) {
        return;
    }
    $.ajax({
        url: "https://blockexplorer.com/api/addr/"+ address +"/balance",
        type: "GET",
        success: function(data){
            q = (data/100000000).toFixed(8);
            a = q.toString().split(".");
            a[0] = a[0].replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,")
            a.join('.');
        },
        error: function(){
            console.log("获取地址余额数据异常");
        }
    });
}

/**
 * 获取比特币地址未确认余额
 * @param address 比特币钱包地址
 */
function getBitCoinUnConfirmedBalance(address){
    if(!address.trim()) {
        return;
    }
    return $.ajax({
        url: "https://blockexplorer.com/api/addr/"+ address +"/unconfirmedBalance",
        type: "GET",
        success: function(data){
            q = (data/100000000).toFixed(8);
            a = q.toString().split(".");
            a[0] = a[0].replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,")
        },
        error: function(){
            console.log("获取地址未确认余额数据异常");
        }
    });
}


/**
 * 获取比特币地址交易记录
 * @param address 比特币钱包地址
 * @return Object 返回交易记录列表
 */
function getBitCoinTransactions(address){
    if(!address.trim()) {
        return;
    }
    return $.ajax({
        url: "https://blockexplorer.com/api/txs?address=" + address,
        type: "GET",
        dataType: "json",
        success: function(data){
            data;
        },
        error: function(){
            console.log("获取地址交易记录数据异常");
        }
    });
}



