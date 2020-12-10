package pl.cinemabookingsystem.cinemabookingsystem.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import pl.cinemabookingsystem.cinemabookingsystem.Repository.MovieRepository;
import pl.cinemabookingsystem.cinemabookingsystem.models.Movie;


@Controller
public class TrackController {

    @Value("${apiKey}")
    private String key;

    private MovieRepository movieRepository;
    @Autowired
    public TrackController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


   public boolean getNewTrack(String title){

       RestTemplate restTemplate = new RestTemplate();
       MultiValueMap<String, String> headers = new HttpHeaders();
       headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);


       HttpEntity httpEntity = new HttpEntity(headers);

       //get data from Api
       JsonNode jsonNode = restTemplate.exchange("http://www.omdbapi.com/?t={title}&plot={full}&apikey={apiKey}", HttpMethod.GET, httpEntity, JsonNode.class,title,"full",key).getBody();
       if (jsonNode.isEmpty()) return false;

       //Change data type
       ObjectMapper objectMapper = new ObjectMapper();
        try {
            Movie movie = objectMapper.readValue(jsonNode.toString(), Movie.class);
            movieRepository.save(movie);
            return true;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }


   }


}
