package com.reportedsocks.movieslistapp.data

data class MoviePreview (val Title: String,
                         val Year: String,
                         val imdbID: String,
                         val Type: String,
                         val Poster: String )

data class Result ( var Search: List<MoviePreview>, val totalResults: String, val Response: String, val Error: String)