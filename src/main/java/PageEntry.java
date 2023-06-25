public class PageEntry implements Comparable<PageEntry> {
    private final String pdfName;
    private final int page;
    private final int count;

    public PageEntry(String pdfName, int page, int count) {
        this.pdfName = pdfName;
        this.page = page;
        this.count = count;
    }

    public String getPdfName() {
        return pdfName;
    }

    public int getPage() {
        return page;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "Name: " + pdfName + " page: " + page + " count: " + count;
    }

    //Сортировка в порядке уменьшения поля count, после по алфавиту и в конце - по возрастанию номера страницы.
    @Override
    public int compareTo(PageEntry pageEntry) {
        if ((this.count == pageEntry.count)
                && (this.pdfName.compareTo(pageEntry.pdfName) == 0)
                && (this.page == pageEntry.page)) {
            return 0;
        } else if ((this.count < pageEntry.count)
                || ((this.count == pageEntry.count) && (this.pdfName.compareTo(pageEntry.pdfName) > 0))
                || ((this.count == pageEntry.count) && (this.pdfName.compareTo(pageEntry.pdfName) == 0) && (this.page > pageEntry.page))) {
            return 1;
        } else {
            return -1;
        }
    }
}
