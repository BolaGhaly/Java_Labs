package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLine {

    public static String executeCommandPrompt(String command) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        StringBuilder output = new StringBuilder();

        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            processBuilder.command("cmd.exe", "/c", command);
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            processBuilder.command("bash", "-c", command);
        } else {
            throw new UnsupportedOperationException("Unsupported OS: " + os);
        }

        try {
            Process process = processBuilder.start();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            int exitVal = process.waitFor();
            if (exitVal != 0) {
                throw new RuntimeException("Command failed with exit code " + exitVal);
            }
        } catch (IOException e) {
            throw new RuntimeException("I/O error while executing command", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Command execution interrupted", e);
        }

        return output.toString().trim();
    }
}
