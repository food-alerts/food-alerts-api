package foodalert.food_alert.services.apicalls;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import foodalert.food_alert.model.entities.Cip13;
import foodalert.food_alert.model.entities.Molecule;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.bind.Element;
import javax.xml.parsers.*;
import javax.xml.ws.handler.Handler;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by malk on 21/03/15.
 */
public class DrugService implements MoleculeService {

    private OkHttpClient httpClient = new OkHttpClient();

    @Override
    public Collection<Molecule> apply(Cip13 cip13) {
        String url = "http://apirest-dev.vidal.fr/rest/api/search?code=" + cip13 + "3400955473350&app_id=9fd557d3&app_key=b5ab4790e151b24971f250200643effd";
        String atom = null;
        try {
            atom = call(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return molecules(atom);
    }

    private String call(String url) throws IOException {
        Request request = new Request.Builder().get().url(url).build();
        return httpClient.newCall(request).execute().body().string();
    }

    private Collection<Molecule> molecules(String atom) throws Exception {
        XPath xpath = XPathFactory.newInstance().newXPath();
        String expression = "/entry/link[@title='PRODUCT']";
        InputSource inputSource = new InputSource(new StringReader(atom));
        NodeList nodes = (NodeList) xpath.evaluate(expression, inputSource, XPathConstants.NODESET);
        if (nodes.getLength() > 0)  {
            //href nodes.item(0).getAttributes().getNamedItem("href").toString();
        }
        return new ArrayList<Molecule>();
    }
}
