import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {
    protected Map<String, List<PageEntry>> map = new HashMap<>(); //Map, где ключом будет слово, а значением - искомый список
    protected PageEntry pageEntry;
    protected List<PageEntry> pageEntryList = new ArrayList<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        // Читает все pdf и сохраняет нужные данные, т.к. во время поиска сервер не должен уже читать файлы.

        for (File file : Objects.requireNonNull(pdfsDir.listFiles())) {
            var doc = new PdfDocument(new PdfReader(file)); //создать объект пдф-документа

            for (int i = 1; i <= doc.getNumberOfPages(); i++) {
                String text = PdfTextExtractor.getTextFromPage(doc.getPage(i)); //получить текст со страницы
                String[] words = text.split("\\P{IsAlphabetic}+"); //разбить текст на слова

                Map<String, Integer> freqs = new HashMap<>();// Map, где ключом будет слово, а значением - частота
                for (var word : words) { // перебираем слова
                    if (word.isEmpty()) {
                        continue;
                    }
                    word = word.toLowerCase();
                    freqs.put(word, freqs.getOrDefault(word, 0) + 1);
                }

                for (String key : freqs.keySet()) {
                    pageEntry = new PageEntry(file.getName(), i, freqs.get(key));
                    pageEntryList = map.getOrDefault(key, new ArrayList<>());
                    pageEntryList.add(pageEntry);
                    Collections.sort(pageEntryList);
                    map.put(key, pageEntryList);
                }
            }
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        //поиск по слову
        List<PageEntry> list = new ArrayList<>();
        word = word.toLowerCase();
        if (map.containsKey(word)) {
            for (String key : map.keySet()) {
                if (key.equals(word)) {
                    list = map.get(word);
                }
            }
            return list;
        }
        return Collections.emptyList();
    }
}