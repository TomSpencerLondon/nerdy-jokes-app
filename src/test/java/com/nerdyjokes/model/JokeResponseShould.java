package com.nerdyjokes.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class JokeResponseShould {

    @Value("classpath:expectedJoke.json")
    private Resource expectedJoke;

    private JacksonTester<JokeResponse> json;

    private JokeResponse jokeResponse;

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());

        JokeResponse.Value value = new JokeResponse.Value(100, "joke", List.of("nerdy"));
        jokeResponse = new JokeResponse("success", value);
    }

    @Test
    void serialize_from_object_to_json() throws Exception {
        assertThat(json.write(jokeResponse))
            .isEqualToJson(expectedJoke);
        assertThat(json.write(jokeResponse))
            .hasJsonPathStringValue("type");
        assertThat(json.write(jokeResponse))
            .extractingJsonPathStringValue("type")
            .isEqualTo("success");
        assertThat(json.write(jokeResponse))
            .hasJsonPathMapValue("value");
        assertThat(json.write(jokeResponse))
            .extractingJsonPathNumberValue("value.id")
            .isEqualTo(100);
        assertThat(json.write(jokeResponse))
            .extractingJsonPathStringValue("value.joke")
            .isEqualTo("joke");
        assertThat(json.write(jokeResponse))
            .extractingJsonPathArrayValue("value.categories")
            .isEqualTo(List.of("nerdy"));
    }

    @Test
    void deserialize_json_to_joke_object() throws Exception {
        final String jsonJoke = getJokeAsJson();

        assertThat(json.parse(jsonJoke))
            .isEqualTo(jokeResponse);
        assertThat(json.parseObject(jsonJoke).getType())
            .isEqualTo("success");
        assertThat(json.parseObject(jsonJoke).getValue().getId())
            .isEqualTo(100);
    }

    private String getJokeAsJson() {
        return "{\"type\":\"success\"," +
            "\"value\":{\"id\":100,\"joke\":\"joke\"," +
            "\"categories\":[\"nerdy\"]}}";
    }
}
