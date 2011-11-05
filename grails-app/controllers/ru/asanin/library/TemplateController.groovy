package ru.asanin.library

class TemplateController {

    def change = {
        if (params.template)
            lib.setTemplateFamily(params.template)
        redirect(uri: "/")
    }
}
