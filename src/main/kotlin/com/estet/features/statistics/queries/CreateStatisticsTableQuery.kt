package com.estet.features.statistics.queries

import com.estet.database.Query

class CreateStatisticsTableQuery : Query {
    override fun getQuery(): String {
        return """
            CREATE TABLE public.statistics
                (
                    id text NOT NULL,
                    user_id text NOT NULL,
                    question_id text NOT NULL,
                    selected_answer_id text NOT NULL,
                    PRIMARY KEY (id)
                );
                
                ALTER TABLE IF EXISTS public.statistics
                    OWNER to estet;
        """.trimIndent()
    }
}