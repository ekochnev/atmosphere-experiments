/*
 * jQuery Http Plugin
 */

jQuery.http = function() {

    return {
        version : 0.1,

        url : "",
        host : "",
        method: 'POST',
        headers : {},
        attributes : {},
        parameters : {},
        cookies : {},
        body : null,

//        HttpRequestBuilder : function(options) {
//
//        },

        GET: function() {
            jQuery.http.method = 'GET';
            return this;
        },

        POST: function() {
            jQuery.http.method = 'POST';
            return this;
        },

        Url: function(url) {
            jQuery.http.url = url;
            return this;
        },

        Host: function(host) {
            jQuery.http.host = host;
            return this;
        },

        addHeader: function(name, value) {

            jQuery.http.headers[name.toString()] = value;
            alert(jQuery.http.headers);
            return this;
        },

        addHeaders: function(headers) {

            jQuery.each(cookies, function(index, value) {
                jQuery.http.cookies[index] = value;
            });
            return this;
        },

        addParameter: function(name, value) {

            jQuery.http.headers[name] = value;
            return this;
        },

        addParameters: function(parameters) {

            jQuery.each(cookies, function(index, value) {
                jQuery.http.cookies[index] = value;
            });
            return this;
        },

        addCookie: function(name, value) {

            jQuery.http.cookies[name] = value;
            return this;
        },

        addCookies: function(cookies) {

            //jQuery.http.cookies.push(cookies);

            jQuery.each(cookies, function(index, value) {
                jQuery.http.cookies[index] = value;
            });
            return this;
        },

        addBody: function(body) {
            jQuery.http.body = body;
            return this;
        },

        Accept: function(value) {
            jQuery.http.headers['Accept'] = value;
            return this;
        },

        AcceptCharset: function(value) {
            jQuery.http.headers['Accept-Charset'] = value;
            return this;
        },

        AcceptEncoding: function(value) {
            jQuery.http.headers['Accept-Encoding'] = value;
            return this;
        },

        RAW : function() {

            var rawHttpRequest = "";
            var nbsp = " ";
            var LF = "\n";
            var CR = "\r";
            var CRLF = "\r\n";
            var EL = " "; // empty line

            rawHttpRequest = rawHttpRequest + jQuery.http.method + nbsp
                + jQuery.http.url + nbsp
                + "HTTP/1.1" + LF
                + "Host:" + nbsp + jQuery.http.host + LF;

            jQuery.each(jQuery.http.headers, function(index, value) {
                rawHttpRequest = rawHttpRequest + index + ":" + nbsp + value + LF;
            });

            jQuery.each(jQuery.http.attributes, function(index, value) {
                rawHttpRequest = rawHttpRequest + index + ":" + nbsp + value + LF;
            });

            jQuery.each(jQuery.http.parameters, function(index, value) {
                rawHttpRequest = rawHttpRequest + index + ":" + nbsp + value + LF;
            });

            jQuery.each(jQuery.http.cookies, function(index, value) {
                rawHttpRequest = rawHttpRequest + index + ":" + nbsp + value + LF;
            });
            rawHttpRequest = rawHttpRequest + CRLF;

            if (jQuery.http.body != null) {
                rawHttpRequest = rawHttpRequest + EL
                    + jQuery.http.body + EL;
            }

            return rawHttpRequest;
        },

        JSON : function() {
            return this;
        }


    };

}();