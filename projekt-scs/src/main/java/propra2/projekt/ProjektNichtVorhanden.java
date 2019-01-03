package propra2.projekt;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Projekt nicht gefunden")
public class ProjektNichtVorhanden extends RuntimeException {

}
