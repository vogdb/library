<html>
    <head>
        <title>Welcome to Grails</title>
        <meta name="layout" content="main" />
        <style type="text/css" media="screen">
            #pageBody {
                margin-left:20px;
                margin-right:20px;
            }
        </style>
    </head>
    <body>
        <div id="pageBody">
            <lib:input name="sample" size="20"/>
            <g:form controller="template" action="change">
                <g:select name="template" from="${lib.getTemplateFamilies()}"/>
                <g:submitButton name="submit" value="watch"/>
            </g:form>
        </div>
    </body>
</html>
