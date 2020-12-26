package hu.xannosz.minecraft.coordinate.holder;

import hu.xannosz.microtools.FileResourcesUtils;
import hu.xannosz.microtools.pack.Douplet;
import hu.xannosz.veneos.core.*;
import hu.xannosz.veneos.core.css.CssAttribute;
import hu.xannosz.veneos.core.css.CssComponent;
import hu.xannosz.veneos.core.css.HtmlSelector;
import hu.xannosz.veneos.core.css.Selector;
import hu.xannosz.veneos.core.html.*;
import hu.xannosz.veneos.next.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Holder implements HttpHandler {
    private static final String NAME = "name";
    private static final String X = "x";
    private static final String Y = "y";
    private static final String Z = "z";
    private static final String DIMENSION = "dimension";
    private static final String VERSION = "0.0.1";

    private final Theme theme = new Theme();
    private final HtmlClass copyButton = new HtmlClass();
    private final HtmlClass formButton = new HtmlClass();

    public static void main(String[] args) {
        Holder holder = new Holder();
    }

    public Holder() {
        VeneosServer server = new VeneosServer();
        server.createServer(8000);
        server.setHandler(this);
        FileContainer.addFile("copy.png", FileResourcesUtils.getFileFromResourceAsFile("copy.png"));
        FileContainer.addFile("map.png", FileResourcesUtils.getFileFromResourceAsFile("map.png"));
        ThemeHandler.registerTheme(theme);

        CssComponent body = new CssComponent(HtmlSelector.BODY.getSelector());
        body.addAttribute(CssAttribute.COLOR, "lightgray");
        body.addAttribute(CssAttribute.BACKGROUND_COLOR, "black");
        body.addAttribute(CssAttribute.TEXT_ALIGN, "center");
        theme.add(body);

        CssComponent input = new CssComponent(HtmlSelector.INPUT.getSelector());
        input.addAttribute(CssAttribute.COLOR, "lightgray");
        input.addAttribute(CssAttribute.BACKGROUND_COLOR, "black");
        input.addAttribute(CssAttribute.MARGIN, "5px");
        input.addAttribute(CssAttribute.PADDING, "5px");
        theme.add(input);

        CssComponent select = new CssComponent(HtmlSelector.SELECT.getSelector());
        select.addAttribute(CssAttribute.COLOR, "lightgray");
        select.addAttribute(CssAttribute.BACKGROUND_COLOR, "black");
        select.addAttribute(CssAttribute.MARGIN, "5px");
        select.addAttribute(CssAttribute.PADDING, "5px");
        theme.add(select);

        CssComponent img = new CssComponent(HtmlSelector.IMG.getSelector());
        img.addAttribute(CssAttribute.WIDTH, "32px");
        img.addAttribute(CssAttribute.HEIGHT, "32px");
        theme.add(img);

        CssComponent table = new CssComponent(HtmlSelector.TABLE.getSelector());
        table.addAttribute(CssAttribute.BORDER_COLOR, "lightgray");
        table.addAttribute(CssAttribute.BORDER_WIDTH, "1px");
        table.addAttribute(CssAttribute.BORDER_STYLE, "solid");
        table.addAttribute(CssAttribute.BORDER_RADIUS, "15px");
        table.addAttribute(CssAttribute.MARGIN, "30px");
        table.addAttribute(CssAttribute.WIDTH, "60%");
        theme.add(table);

        CssComponent th = new CssComponent(HtmlSelector.TH.getSelector());
        th.addAttribute(CssAttribute.TEXT_DECORATION, "underline");
        theme.add(th);

        CssComponent tr = new CssComponent(new Selector("tr:nth-child(even), tr:nth-child(even) button"));
        tr.addAttribute(CssAttribute.BACKGROUND_COLOR, "#101010");
        theme.add(tr);

        CssComponent a = new CssComponent(HtmlSelector.A.getSelector());
        a.addAttribute(CssAttribute.DISPLAY, "block");
        a.addAttribute(CssAttribute.COLOR, "lightgray");
        a.addAttribute(CssAttribute.MARGIN, "2% 5%");
        a.addAttribute(CssAttribute.PADDING, "5%");
        a.addAttribute(CssAttribute.TEXT_DECORATION, "none");
        theme.add(a);

        CssComponent button = new CssComponent(HtmlSelector.BUTTON.getSelector());
        button.addAttribute(CssAttribute.COLOR, "lightgray");
        button.addAttribute(CssAttribute.BORDER_COLOR, "lightgray");
        button.addAttribute(CssAttribute.BORDER_WIDTH, "1px");
        button.addAttribute(CssAttribute.BORDER_STYLE, "solid");
        button.addAttribute(CssAttribute.BORDER_RADIUS, "15px");
        button.addAttribute(CssAttribute.MARGIN, "1%");
        button.addAttribute(CssAttribute.PADDING, "2%");
        button.addAttribute(CssAttribute.BACKGROUND_COLOR, "black");
        theme.add(button);

        CssComponent copyButtonCss = new CssComponent(new Selector("." + copyButton + " " + HtmlSelector.BUTTON));
        copyButtonCss.addAttribute(CssAttribute.BORDER, "0");
        theme.add(copyButtonCss);

        CssComponent formButtonCss = new CssComponent(new Selector("." + formButton + ", " + "." + formButton + " " + HtmlSelector.BUTTON));
        formButtonCss.addAttribute(CssAttribute.MARGIN, "10px");
        formButtonCss.addAttribute(CssAttribute.PADDING, "10px");
        theme.add(formButtonCss);

        CssComponent aHover = new CssComponent(new Selector(HtmlSelector.A + ":hover"));
        aHover.addAttribute(CssAttribute.COLOR, "white");
        aHover.addAttribute(CssAttribute.BORDER_COLOR, "white");
        theme.add(aHover);

        CssComponent buttonHover = new CssComponent(new Selector(HtmlSelector.BUTTON + ":hover"));
        buttonHover.addAttribute(CssAttribute.COLOR, "white");
        buttonHover.addAttribute(CssAttribute.BORDER_COLOR, "white");
        theme.add(buttonHover);

        CssComponent inputHover = new CssComponent(new Selector(HtmlSelector.INPUT + ":hover"));
        inputHover.addAttribute(CssAttribute.COLOR, "white");
        inputHover.addAttribute(CssAttribute.BORDER_COLOR, "white");
        theme.add(inputHover);

        CssComponent footer = new CssComponent(HtmlSelector.FOOTER.getSelector());
        footer.addAttribute(CssAttribute.FLOAT, "down");
        theme.add(footer);
    }

    @Override
    public Douplet<Integer, Page> getResponse(RequestMethod requestMethod, String s, Map<String, String> map) {
        Data.readData();

        if (s.equals("/add")) {
            Page addPage = new Page();
            addPage.setTitle("Add coordinate");
            addPage.addTheme(theme);
            Form form = new Form("/", false);

            form.add(new Label(NAME, "Name of the coordinate : "));
            Input name = new Input("text");
            name.setName(NAME);
            form.add(name);
            form.add(new StringHtmlComponent(StringModifiers.BR.toString()));

            form.add(new Label(X, "X coordinate ( integer ) : "));
            Input x = new Input("text");
            x.setName(X);
            form.add(x);
            form.add(new StringHtmlComponent(StringModifiers.BR.toString()));

            form.add(new Label(Y, "Y coordinate ( integer ) : "));
            Input y = new Input("text");
            y.setName(Y);
            form.add(y);
            form.add(new StringHtmlComponent(StringModifiers.BR.toString()));

            form.add(new Label(Z, "Z coordinate ( integer ) : "));
            Input z = new Input("text");
            z.setName(Z);
            form.add(z);
            form.add(new StringHtmlComponent(StringModifiers.BR.toString()));

            form.add(new Label(DIMENSION, "Dimension: "));
            Select selectDim = new Select(DIMENSION);
            for (Data.Dimension dim : Data.Dimension.values()) {
                selectDim.add(new Option(dim.name(), dim.name()));
            }
            form.add(selectDim);
            form.add(new StringHtmlComponent(StringModifiers.BR.toString()));

            Button okButton = new Button("OK");
            okButton.addClass(formButton);
            okButton.setSubmit();
            form.add(okButton);

            addPage.addComponent(form);
            addPage.addComponent(new OneButtonForm("/", "Cancel", false).addClass(formButton));
            return new Douplet<>(200, addPage);
        }

        if (map.get(NAME) != null) {
            try {
                Data.INSTANCE.addCoordinate(map.get(NAME), Integer.parseInt(map.get(X)),
                        Integer.parseInt(map.get(Y)), Integer.parseInt(map.get(Z)),
                        Data.Dimension.valueOf(map.get(DIMENSION)));
                Data.writeData();
                Page redirect = new Page();
                redirect.addComponent(new Redirect("/", redirect));
                return new Douplet<>(200, redirect);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Page page = new Page();
        page.setTitle("Coordinates");
        page.addTheme(theme);

        Table table = new Table();
        table.addHead("Name");
        table.addHead("Dimension");
        table.addHead("X");
        table.addHead("Z");

        List<Data.Coordinate> coordinates = new ArrayList<>(Data.INSTANCE.getCoordinates());
        coordinates.sort(new Data.CoordinateComparator());
        for (Data.Coordinate coordinate : coordinates) {
            table.add(coordinate.getName());
            table.add("" + coordinate.getDimension());
            table.add("" + (coordinate.getX() / 50) * 50);
            table.add("" + (coordinate.getZ() / 50) * 50);
            table.add(new A(Data.INSTANCE.getMapPrefix() + getMapCoordinates(coordinate), new Img("/files/map.png")));
            table.add(new CopyButton(new Img("/files/copy.png"), page, getTpCommand(coordinate)).addClass(copyButton));
            table.newRow();
        }

        page.addComponent(table);

        page.addComponent(new FixedButton("/add", "Add new", new ButtonPosition("20px", "5%"), false));
        page.addComponent(new FixedButton(Data.INSTANCE.getMainPage(), "Main page", new ButtonPosition("20px", "15%"), false));

        page.addComponent(new Footer().add(new P("Version: " + VERSION)));
        return new Douplet<>(200, page);
    }

    private String getTpCommand(Data.Coordinate coordinate) {
        return "tp @p " + coordinate.getX() + " " + coordinate.getY() + " " + coordinate.getZ();
    }

    private String getMapCoordinates(Data.Coordinate coordinate) {
        return "index.html#/" + coordinate.getX() + "/64/" + coordinate.getZ() + "/-2/" + coordinate.getDimension().getLink();
    }
}
