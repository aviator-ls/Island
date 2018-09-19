<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>index</title>
</head>
<body>
    <div>Hello,
    <#if user.nickName??>
        ${user.nickName}
    <#else>
        ${user.userName}
    </#if>
    ${age!}</div>
</body>
</html>