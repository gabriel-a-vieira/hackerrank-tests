package org.example.restapi.intermediate;


import com.google.gson.Gson;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Scanner;

/**
 * Complete the 'getTotalGoals' function below.
 * <p>
 * The function is expected to return an INTEGER.
 * The function accepts following parameters:
 * 1. STRING team
 * 2. INTEGER year
 **/

public class TotalGoalsByATeam {

    class Result {

        public static class ResponseDTO {

            public int per_page;
            public int total;
            public int total_pages;
            public List<DataDTO> data;

            public static class DataDTO {

                public String competition;
                public int year;
                public String team1;
                public String team2;
                public String team1goals;
                public String team2goals;

            }

        }

        public static int getTotalGoals(String team, int year) {

            HttpClient client = HttpClient.newBuilder().build();

            try {

                int totalGoals = 0;
                int totalPages = 1;
                int teamNumber = 1;

                for (int i = 1; i <= totalPages; i++) {

                    HttpRequest requestTeam = HttpRequest.newBuilder()
                            .uri(new URI("https://jsonmock.hackerrank.com/api/football_matches?year=" + year + "&team" + teamNumber + "=" + team + "&page=" + i))
                            .GET()
                            .build();

                    HttpResponse<String> responseTeam = client.send(requestTeam, HttpResponse.BodyHandlers.ofString());

                    if (responseTeam != null) {

                        Gson gson = new Gson();
                        ResponseDTO responseDTO = gson.fromJson(responseTeam.body(), ResponseDTO.class);

                        if (responseDTO != null) {

                            totalPages = responseDTO.total_pages;

                            if (responseDTO.data != null) {

                                for (ResponseDTO.DataDTO game : responseDTO.data) {

                                    if (game.team1 != null && game.team1.equalsIgnoreCase(team) && game.team1goals != null) {
                                        totalGoals += Integer.valueOf(game.team1goals);
                                    } else if (game.team1 != null && game.team2.equalsIgnoreCase(team) && game.team2goals != null) {
                                        totalGoals += Integer.valueOf(game.team2goals);
                                    }

                                }

                            }

                        }

                    }

                    if (i == totalPages && teamNumber == 1) {

                        teamNumber++;
                        i = 0;
                        totalPages = 1;

                    }

                }

                return totalGoals;

            } catch (Exception e) {
                e.printStackTrace();
            }

            return 0;

        }

    }

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter team name: ");
        String team = scanner.nextLine();

        System.out.println("Enter the championship year: ");
        int year = scanner.nextInt();

        int result = Result.getTotalGoals(team, year);

        System.out.println("Total Goals for " + team + " in " + year + " = " + result);

    }

}