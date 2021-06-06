package statical;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import client.POST_HOC;
import client.StacConsumer;
import model.NonParametricTestAll;
import model.PostHocResult;
import model.RankingResult;

public class BordaRanking {
    public static void main(String[] args) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
        String path = "src/main/resources/Toxina-Friedman-min3.csv";
        NonParametricTestAll friedman = StacConsumer.FRIEDMAN(path, 0.05, POST_HOC.FINNER);
        System.out.println("Friedman: " + friedman);
        HashMap<String, Double> doRankingBorder = doRankingBorda(friedman);
        System.out.println(doRankingBorder);
    }

    /**
     * Realiza rankeo borda de un estadisitico multi
     * 
     * @param stacResultObject map devuelto por stac
     * @return Un map de todos los objetos con su rankeo, no ordenado.
     */
    public static HashMap<String, Double> doRankingBorda(NonParametricTestAll nonParametricResult) {
        HashMap<String, Double> rs = new HashMap<>();
        if (nonParametricResult.getRanking()!=null) {
            RankingResult post_hocranking = nonParametricResult.getRanking();
            String[] _names = post_hocranking.getNames();
            HashMap<String, ArrayList<String>> comparisonHashMap = new HashMap<>();
            for (String string : _names) {
                comparisonHashMap.put(string, new ArrayList<>());
            }

            PostHocResult post_hoc = nonParametricResult.getPost_hoc();
            String[] comparisons = post_hoc.getComparisons();
            BigDecimal[] result = post_hoc.getResult();

            for (int i = 0; i < result.length; i++) {
                if (result[i].compareTo(BigDecimal.ONE) ==0) {
                    String a = null, b = null;
                    for (String name : _names) {
                        String[] aVSb = comparisons[i].split(" ");
                        if (aVSb[0].equals(name) || aVSb[2].equals(name)) {
                            if (a == null) {
                                a = name;
                            } else if (b == null) {
                                b = name;
                                break;
                            }
                        }
                    }
                    comparisonHashMap.get(a).add(b);
                    comparisonHashMap.get(b).add(a);
                }
            }
            // System.out.println(comparisonHashMap);
            while (!comparisonHashMap.isEmpty()) {
                ArrayList<String> max = getMax(comparisonHashMap);
                ArrayList<Integer> index = new ArrayList<>();
                ArrayList<String> keys = new ArrayList<>();
                boolean wasZero = max.size() == 0;
                for (int i = 0; i < _names.length; i++) {
                    String string = _names[i];
                    if (comparisonHashMap.containsKey(string) && comparisonHashMap.get(string).size() == max.size()) {
                        comparisonHashMap.remove(string);
                        index.add(i + 1);
                        keys.add(string);
                    }
                }
                double sum = 0;
                for (Integer integer : index) {
                    sum += integer;
                }
                if (wasZero) {
                    double tmp = -1;
                    int lastIndex = -1;
                    double tmpSum = sum / index.size();
                    boolean exist = false;
                    for (int i = 0; i < _names.length; i++) {
                        if (rs.containsKey(_names[i])) {
                            lastIndex = i + 1;
                            tmp = rs.get(_names[i]);
                            if (!exist && tmpSum == tmp) {
                                exist = true;
                            }
                        }
                    }
                    if (exist) {
                        while (lastIndex <= tmp) {
                            lastIndex++;
                        }
                        sum = lastIndex;
                    } else {
                        sum /= index.size();
                    }
                } else {
                    sum /= index.size();
                }
                for (int i = 0; i < index.size(); i++) {
                    rs.put(keys.get(i), sum);
                }
            }

        }
        return rs;
    }

    private static ArrayList<String> getMax(HashMap<String, ArrayList<String>> comparisonHashMap) {
        String key = null;
        ArrayList<String> result = null;
        Iterator<String> iterator = comparisonHashMap.keySet().iterator();
        while (iterator.hasNext()) {
            key = iterator.next();
            if (result == null) {
                result = comparisonHashMap.get(key);
            } else if (result.size() < comparisonHashMap.get(key).size()) {
                result = comparisonHashMap.get(key);
            }
        }
        return result;
    }

    /**
     * Realiza rankeo de todos los casos de una metrica particular.
     * 
     * @param rankList Map con rakeo Borda.
     * @return retorna el rankeo global de los objetos trasladados, no ordenado.
     */
    public static HashMap<String, Double> doGlobalRanking(ArrayList<HashMap<String, Double>> rankList) {
        HashMap<String, Double> rs = new HashMap<>();

        for (HashMap<String, Double> map : rankList) {
            Iterator<String> keys = map.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                if (rs.containsKey(key)) {
                    rs.put(key, rs.get(key) + map.get(key));
                } else {
                    rs.put(key, map.get(key));
                }
            }
        }
        Iterator<String> iterator = rs.keySet().iterator();
        Double[] array = rs.values().toArray(new Double[rs.values().size()]);
        Arrays.sort(array);
        ArrayList<Double> rank = new ArrayList<>();
        for (Double tmp : array) {
            if (!rank.contains(tmp)) {
                rank.add(tmp);
            }
        }
        System.out.println(Arrays.toString(array) + " -> " + rank);
        while (iterator.hasNext()) {
            String key = iterator.next();
            int _index = 0;
            for (int i = 0; i < rank.size(); i++) {
                if (Double.compare(rs.get(key), rank.get(i)) == 0) {
                    _index = i + 1;
                    break;
                }
            }
            rs.put(key, (1.0) * _index);
        }
        return rs;
    }

    /**
     * Suma de todos los borda ranks.
     * 
     * @param metricList lista con borda ranks a sumar.
     * @return Map con suma de ranking ordenado.
     */
    public static HashMap<String, Double> makeSumRank(HashMap<String, ArrayList<HashMap<String, Double>>> metricList) {
        HashMap<String, Double> rs = new HashMap<>();
        Iterator<String> iterator = metricList.keySet().iterator();
        while (iterator.hasNext()) {
            String metric = iterator.next();
            for (HashMap<String, Double> map : metricList.get(metric)) {
                Iterator<String> keyIterator = map.keySet().iterator();
                while (keyIterator.hasNext()) {
                    String key = keyIterator.next();
                    if (rs.containsKey(key)) {
                        rs.put(key, rs.get(key) + map.get(key));
                    } else {
                        rs.put(key, map.get(key));
                    }
                }
            }
        }
        return sortByValue(rs);
    }

    // function to sort hashmap by values
    public static HashMap<String, Double> sortByValue(HashMap<String, Double> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Double>> list = new LinkedList<Map.Entry<String, Double>>(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Double> temp = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
