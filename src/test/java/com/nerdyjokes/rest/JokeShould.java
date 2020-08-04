package com.nerdyjokes.rest;

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
class JokeShould {

    @Value("classpath:expectedJoke.json")
    private Resource expectedJoke;

    private JacksonTester<Joke> json;

    private Joke joke;

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());

        Joke.Value value = new Joke.Value(100, "joke", List.of("nerdy"));
        joke = new Joke("success", value);
    }

    @Test
    void serialize_from_object_to_json() throws Exception {
        assertThat(json.write(joke))
            .isEqualToJson(expectedJoke);
        assertThat(json.write(joke))
            .hasJsonPathStringValue("type");
        assertThat(json.write(joke))
            .extractingJsonPathStringValue("type")
            .isEqualTo("success");
        assertThat(json.write(joke))
            .hasJsonPathMapValue("value");
        assertThat(json.write(joke))
            .extractingJsonPathNumberValue("value.id")
            .isEqualTo(100);
        assertThat(json.write(joke))
            .extractingJsonPathStringValue("value.joke")
            .isEqualTo("joke");
        assertThat(json.write(joke))
            .extractingJsonPathArrayValue("value.categories")
            .isEqualTo(List.of("nerdy"));
    }

    @Test
    void deserialize_json_to_joke_object() throws Exception {
        final String jsonJoke = getJokeAsJson();

        assertThat(json.parse(jsonJoke))
            .isEqualTo(joke);
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
