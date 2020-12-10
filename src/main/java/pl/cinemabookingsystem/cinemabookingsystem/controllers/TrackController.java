package pl.cinemabookingsystem.cinemabookingsystem.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.buf.Utf8Decoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;
import pl.cinemabookingsystem.cinemabookingsystem.Repository.TrackRepository;
import pl.cinemabookingsystem.cinemabookingsystem.models.Track;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;

@Controller
public class TrackController {

    @Value("${apiKey}")
    private String key;

    private TrackRepository trackRepository;
    @Autowired
    public TrackController(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
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
            Track track = objectMapper.readValue(jsonNode.toString(),Track.class);
            trackRepository.save(track);
            return true;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }


   }


}
