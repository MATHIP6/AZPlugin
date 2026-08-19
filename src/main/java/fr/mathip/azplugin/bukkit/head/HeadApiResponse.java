package fr.mathip.azplugin.bukkit.head;

import java.util.List;

import lombok.Getter;

@Getter
public class HeadApiResponse {
    private final int total;
    private final int page;
    private final int perPage;
    private final int totalPages;
    private final List<HeadData> heads;

    public HeadApiResponse(int total, int page, int perPage, int totalPages, List<HeadData> heads) {
        this.total = total;
        this.page = page;
        this.perPage = perPage;
        this.totalPages = totalPages;
        this.heads = heads;
    }
}
