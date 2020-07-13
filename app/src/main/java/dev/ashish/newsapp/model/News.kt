package dev.ashish.newsapp.model

class News {
    var status: String? = null
    var totalResults: Int? = null
    var articles: List<Article>? = null

    inner class Article {
        var source: Source? = null
        var author: String? = null
        var title: String? = null
        var description: String? = null
        var url: String? = null
        var urlToImage: String? = null
        var publishedAt: String? = null
        var content: String? = null

    }

    inner class Source {
        var id: Any? = null
        var name: String? = null

    }
}