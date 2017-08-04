/**
 *
 * 环境配置，及登陆逻辑
 *
 **/
(function(owner) {
    /**
     * 默认配置信息
     */
    var serverUrl = 'https://192.168.0.191:8443'


    // VARIABLES =============================================================
    var TOKEN_KEY = "$token";
    var USER_KEY = "$user";

    /**
     * 常量定义
     */
    owner.ADMIN = "ROLE_ADMIN";	// 超级管理员
    owner.USER = "ROLE_USER";	// 管理员



    owner.createAuthorizationTokenHeader = function () {
        var token = owner.getToken();
        if (token) {
            return {"Authorization": token};
        } else {
            return {};
        }
    }

    /**
     * 设置用户信息
     **/
    owner.setUserInfo = function(userInfo) {
        userInfo = userInfo || {};
        localStorage.setItem(USER_KEY, JSON.stringify(userInfo));
    }

    /**
     * 获取用户信息， 不存在返回空对象
     * @return {Object} 登陆用户信息
     **/
    owner.getUserInfo = function() {
        var userInfo = localStorage.getItem(USER_KEY) || '{}';
        return JSON.parse(userInfo);
    }

    /**
     * 获取当前Token
     **/
    owner.getToken = function() {
        var state = localStorage.getItem(TOKEN_KEY) || '{}';
        return JSON.parse(state);
    };

    /**
     * 设置当前Token
     **/
    owner.setToken = function(state) {
        state = state || {};
        localStorage.setItem(TOKEN_KEY, JSON.stringify(state));
    };

    /**
     * 获取当前服务器地址
     **/
    owner.getServerUrl = function() {
        return serverUrl;
    };
}(window.app = {}));