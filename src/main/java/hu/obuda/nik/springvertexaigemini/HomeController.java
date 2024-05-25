package hu.obuda.nik.springvertexaigemini;

import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final VertexAiGeminiChatModel chatModel;

    @Autowired
    public HomeController(VertexAiGeminiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/")
    public String home(Model model) {
        return "index";
    }

    @GetMapping("/chat")
    public String chat(@RequestParam("message") String message, Model model) {
        String response = chatModel.call(message);
        model.addAttribute("response", response);
        return "index";
    }
}
