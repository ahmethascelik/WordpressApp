package com.teb.wordpressapp.config

class AppConfig {

    companion object{
        const val ENDPOINT = "https://minimalistbaker.com/"
        const val HTML_HEADER = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "\n" +
                "<style>\n" +
                "    .wp-block-image img{\n" +
                "        width: 200px !important;\n" +
                "        height: 200px !important;\n" +
                "    }\n" +
                "\n" +
                "    iframe{\n" +
                "        width: 200px !important;\n" +
                "        height: 200px !important;\n" +
                "    }\n" +
                "</style>"

        const val STATUS_BAR_COLOR = "#FFDED6"
        const val STATUS_BAR_BLACK_TEXT: Boolean = true


    }
}