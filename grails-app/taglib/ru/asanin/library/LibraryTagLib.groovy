package ru.asanin.library

class LibraryTagLib {

    static final List TEMPLATE_FAMILIES = ["default", "custom"]
    static namespace = "lib"
    static returnObjectForTags = ["getTemplateFamilies"]

    String templateFamily = TEMPLATE_FAMILIES[0]

    def getTemplateFamilies = {
        return TEMPLATE_FAMILIES
    }

    def setTemplateFamily = {
        templateFamily = it
    }

    def input = {attrs ->
        out << render(template: "/templates/${templateFamily}/input", model: [attrs: attrs])
    }
}
