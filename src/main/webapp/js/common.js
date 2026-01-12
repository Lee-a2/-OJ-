(function (window, $) {
  if (!$) {
    console.error("jQuery is required for common.js");
    return;
  }

  const redirectToLogin = () => window.location.assign("login.html");
  const redirectToIndex = () => window.location.assign("index.html");

  function request(options) {
    const {
      url,
      method = "GET",
      data,
      success = () => {},
      error,
      contentType,
    } = options;

    const ajaxConfig = {
      url,
      type: method,
      success,
      error: error || redirectToLogin,
    };

    if (data !== undefined) {
      ajaxConfig.data = JSON.stringify(data);
      ajaxConfig.contentType = contentType || "application/json; charset=UTF-8";
    }

    $.ajax(ajaxConfig);
  }

  function ensureAdmin(onResolved) {
    request({
      url: "admin",
      success(data) {
        if (data && data.isAdmin) {
          onResolved && onResolved(data);
        } else {
          redirectToIndex();
        }
      },
    });
  }

  function ensureSession(onResolved) {
    request({
      url: "admin",
      success(data) {
        onResolved && onResolved(data);
      },
    });
  }

  window.OJ = {
    request,
    ensureAdmin,
    ensureSession,
    redirectToLogin,
    redirectToIndex,
  };
})(window, window.jQuery);

