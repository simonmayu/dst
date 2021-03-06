package com.distkv.dst.parser;

import com.distkv.dst.common.RequestTypeEnum;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.ListProtocol;
import com.distkv.dst.rpc.protobuf.generated.SetProtocol;
import com.distkv.dst.rpc.protobuf.generated.StringProtocol;
import com.distkv.dst.rpc.protobuf.generated.SortedListProtocol;
import com.distkv.dst.rpc.protobuf.generated.DictProtocol;
import com.google.common.base.Preconditions;
import org.antlr.v4.runtime.tree.ParseTree;
import com.distkv.dst.parser.generated.DstNewSQLBaseListener;
import com.distkv.dst.parser.generated.DstNewSQLParser;
import com.distkv.dst.parser.po.DstParsedResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;


public class DstNewSqlListener extends DstNewSQLBaseListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(DstNewSqlListener.class);

  private DstParsedResult parsedResult = null;

  public DstParsedResult getParsedResult() {
    return parsedResult;
  }

  @Override
  public void enterStrPut(DstNewSQLParser.StrPutContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    StringProtocol.PutRequest.Builder builder = StringProtocol.PutRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setValue(ctx.children.get(2).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.STR_PUT, builder.build());
  }

  @Override
  public void enterStrGet(DstNewSQLParser.StrGetContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    StringProtocol.GetRequest.Builder builder = StringProtocol.GetRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.STR_GET, builder.build());
  }

  @Override
  public void enterStrDrop(DstNewSQLParser.StrDropContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    CommonProtocol.DropRequest.Builder builder = CommonProtocol.DropRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.STR_DROP, builder.build());
  }

  @Override
  public void enterListPut(DstNewSQLParser.ListPutContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    ListProtocol.PutRequest.Builder builder = ListProtocol.PutRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    final int valueSize = ctx.children.get(2).getChildCount();
    for (int i = 0; i < valueSize; ++i) {
      builder.addValues(ctx.children.get(2).getChild(i).getText());
    }
    parsedResult = new DstParsedResult(RequestTypeEnum.LIST_PUT, builder.build());
  }

  @Override
  public void enterListLput(DstNewSQLParser.ListLputContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    ListProtocol.LPutRequest.Builder builder = ListProtocol.LPutRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    final int valueSize = ctx.children.get(2).getChildCount();
    for (int i = 0; i < valueSize; ++i) {
      builder.addValues(ctx.children.get(2).getChild(i).getText());
    }
    parsedResult = new DstParsedResult(RequestTypeEnum.LIST_LPUT, builder.build());
  }

  @Override
  public void enterListRput(DstNewSQLParser.ListRputContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    ListProtocol.RPutRequest.Builder builder = ListProtocol.RPutRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    final int valueSize = ctx.children.get(2).getChildCount();
    for (int i = 0; i < valueSize; ++i) {
      builder.addValues(ctx.children.get(2).getChild(i).getText());
    }
    parsedResult = new DstParsedResult(RequestTypeEnum.LIST_RPUT, builder.build());
  }

  @Override
  public void enterListGetAll(DstNewSQLParser.ListGetAllContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 1);

    ListProtocol.GetRequest.Builder getRequestBuilder = ListProtocol.GetRequest.newBuilder();
    getRequestBuilder.setKey(ctx.getChild(0).getText());
    getRequestBuilder.setType(ListProtocol.GetType.GET_ALL);

    parsedResult = new DstParsedResult(RequestTypeEnum.LIST_GET, getRequestBuilder.build());
  }

  @Override
  public void enterListGetOne(DstNewSQLParser.ListGetOneContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    ListProtocol.GetRequest.Builder getRequestBuilder = ListProtocol.GetRequest.newBuilder();
    getRequestBuilder.setKey(ctx.getChild(0).getText());
    getRequestBuilder.setIndex(Integer.valueOf(ctx.getChild(1).getText()));
    getRequestBuilder.setType(ListProtocol.GetType.GET_ONE);

    parsedResult = new DstParsedResult(RequestTypeEnum.LIST_GET, getRequestBuilder.build());
  }

  @Override
  public void enterListGetRange(DstNewSQLParser.ListGetRangeContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    ListProtocol.GetRequest.Builder getRequestBuilder = ListProtocol.GetRequest.newBuilder();
    getRequestBuilder.setKey(ctx.getChild(0).getText());
    getRequestBuilder.setFrom(Integer.valueOf(ctx.getChild(1).getText()));
    getRequestBuilder.setEnd(Integer.valueOf(ctx.getChild(2).getText()));
    getRequestBuilder.setType(ListProtocol.GetType.GET_RANGE);

    parsedResult = new DstParsedResult(RequestTypeEnum.LIST_GET, getRequestBuilder.build());
  }

  @Override
  public void enterListRemoveOne(DstNewSQLParser.ListRemoveOneContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    ListProtocol.RemoveRequest.Builder removeRequestBuilder =
            ListProtocol.RemoveRequest.newBuilder();

    removeRequestBuilder.setType(ListProtocol.RemoveType.RemoveOne);
    removeRequestBuilder.setKey(ctx.children.get(0).getText());
    removeRequestBuilder.setIndex(Integer.valueOf(ctx.children.get(1).getText()));
    parsedResult = new DstParsedResult(RequestTypeEnum.LIST_REMOVE, removeRequestBuilder.build());
  }

  @Override
  public void enterListRemoveRange(DstNewSQLParser.ListRemoveRangeContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    ListProtocol.RemoveRequest.Builder removeRequestBuilder =
            ListProtocol.RemoveRequest.newBuilder();

    removeRequestBuilder.setType(ListProtocol.RemoveType.RemoveRange);
    removeRequestBuilder.setKey(ctx.children.get(0).getText());
    removeRequestBuilder.setFrom(Integer.valueOf(ctx.children.get(1).getText()));
    removeRequestBuilder.setEnd(Integer.valueOf(ctx.children.get(2).getText()));
    parsedResult = new DstParsedResult(RequestTypeEnum.LIST_REMOVE, removeRequestBuilder.build());
  }

  @Override
  public void enterListMRemove(DstNewSQLParser.ListMRemoveContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() >= 3);

    ListProtocol.MRemoveRequest.Builder mremoveRequest = ListProtocol.MRemoveRequest.newBuilder();
    mremoveRequest.setKey(ctx.children.get(1).getText());

    List<Integer> indexesList = new ArrayList<>();
    for (int i = 2; i < ctx.children.size(); i++) {
      indexesList.add(Integer.valueOf(ctx.children.get(i).getText()));
    }
    mremoveRequest.addAllIndexes(indexesList);

    parsedResult = new DstParsedResult(RequestTypeEnum.LIST_M_REMOVE, mremoveRequest.build());
  }

  @Override
  public void enterListDrop(DstNewSQLParser.ListDropContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    CommonProtocol.DropRequest.Builder builder = CommonProtocol.DropRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.LIST_DROP, builder.build());
  }

  @Override
  public void enterSetPut(DstNewSQLParser.SetPutContext ctx) {
    // The children of the `set_put` should be 3:
    //        `set.put    key     value_array`
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SetProtocol.PutRequest.Builder builder = SetProtocol.PutRequest.newBuilder();
    final String key = ctx.children.get(1).getText();
    builder.setKey(key);
    final int valueSize = ctx.children.get(2).getChildCount();
    for (int i = 0; i < valueSize; ++i) {
      builder.addValues(ctx.children.get(2).getChild(i).getText());
    }
    SetProtocol.PutRequest request = builder.build();
    parsedResult = new DstParsedResult(RequestTypeEnum.SET_PUT, request);
  }

  @Override
  public void enterSetGet(DstNewSQLParser.SetGetContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);
    SetProtocol.GetRequest.Builder builder = SetProtocol.GetRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.SET_GET, builder.build());
  }

  @Override
  public void enterSetDrop(DstNewSQLParser.SetDropContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);
    CommonProtocol.DropRequest.Builder builder = CommonProtocol.DropRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.SET_DROP, builder.build());
  }

  @Override
  public void enterSetPutItem(DstNewSQLParser.SetPutItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    SetProtocol.PutItemRequest.Builder builder = SetProtocol.PutItemRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setItemValue(ctx.children.get(2).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.SET_PUT_ITEM, builder.build());
  }

  @Override
  public void enterSetRemoveItem(DstNewSQLParser.SetRemoveItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.getChildCount() == 3);
    SetProtocol.RemoveItemRequest.Builder builder = SetProtocol.RemoveItemRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setItemValue(ctx.children.get(2).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.SET_REMOVE_ITEM, builder.build());
  }

  @Override
  public void enterSetExists(DstNewSQLParser.SetExistsContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    SetProtocol.ExistsRequest.Builder builder = SetProtocol.ExistsRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setEntity(ctx.children.get(2).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.SET_EXIST, builder.build());
  }

  @Override
  public void enterDictPut(DstNewSQLParser.DictPutContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    DictProtocol.PutRequest.Builder builder = DictProtocol.PutRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    final ParseTree keyValuePairsParseTree = ctx.children.get(2);
    final int numKeyValuePairs = keyValuePairsParseTree.getChildCount();
    DictProtocol.DstDict.Builder dstDictBuilder = DictProtocol.DstDict.newBuilder();
    for (int i = 0; i < numKeyValuePairs; ++i) {
      final ParseTree keyValuePairParseTree = keyValuePairsParseTree.getChild(i);
      Preconditions.checkState(keyValuePairParseTree.getChildCount() == 2);
      dstDictBuilder.addKeys(keyValuePairParseTree.getChild(0).getText());
      dstDictBuilder.addValues(keyValuePairParseTree.getChild(1).getText());
    }
    builder.setDict(dstDictBuilder.build());
    parsedResult = new DstParsedResult(RequestTypeEnum.DICT_PUT, builder.build());
  }

  @Override
  public void enterDictGet(DstNewSQLParser.DictGetContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);
    DictProtocol.GetRequest.Builder builder = DictProtocol.GetRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.DICT_GET, builder.build());
  }

  @Override
  public void enterDictPutItem(DstNewSQLParser.DictPutItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 4);
    DictProtocol.PutItemRequest.Builder builder = DictProtocol.PutItemRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setItemKey(ctx.children.get(2).getText());
    builder.setItemValue(ctx.children.get(3).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.DICT_PUT_ITEM, builder.build());
  }

  @Override
  public void enterDictGetItem(DstNewSQLParser.DictGetItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    DictProtocol.GetItemRequest.Builder builder
        = DictProtocol.GetItemRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setItemKey(ctx.children.get(2).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.DICT_GET_ITEM, builder.build());
  }

  @Override
  public void enterDictPopItem(DstNewSQLParser.DictPopItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    DictProtocol.PopItemRequest.Builder builder = DictProtocol.PopItemRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setItemKey(ctx.children.get(2).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.DICT_POP_ITEM, builder.build());
  }

  @Override
  public void enterDictRemoveItem(DstNewSQLParser.DictRemoveItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    DictProtocol.RemoveItemRequest.Builder builder = DictProtocol.RemoveItemRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setItemKey(ctx.children.get(2).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.DICT_REMOVE_ITEM, builder.build());
  }

  @Override
  public void enterDictDrop(DstNewSQLParser.DictDropContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);
    CommonProtocol.DropRequest.Builder builder = CommonProtocol.DropRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.DICT_DROP, builder.build());
  }

  @Override
  public void enterSlistPut(DstNewSQLParser.SlistPutContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SortedListProtocol.PutRequest.Builder slistPutRequestBuilder =
            SortedListProtocol.PutRequest.newBuilder();
    final ParseTree sortedListEntityPairsParseTree = ctx.children.get(2);
    final int sortedListEntityPairs = sortedListEntityPairsParseTree.getChildCount();

    slistPutRequestBuilder.setKey(ctx.children.get(1).getText());
    for (int i = 0; i < sortedListEntityPairs; i++) {
      final SortedListProtocol.SortedListEntity.Builder slistBuilder =
              SortedListProtocol.SortedListEntity.newBuilder();
      final ParseTree sortedListEntityParseTree =
              sortedListEntityPairsParseTree.getChild(i);
      Preconditions.checkState(sortedListEntityParseTree.getChildCount() == 2);
      slistBuilder.setScore(Integer.valueOf(sortedListEntityParseTree.getChild(1).getText()));
      slistBuilder.setMember(sortedListEntityParseTree.getChild(0).getText());
      slistPutRequestBuilder.addList(slistBuilder);
    }

    parsedResult = new DstParsedResult(RequestTypeEnum.SLIST_PUT, slistPutRequestBuilder.build());
  }

  @Override
  public void enterSlistTop(DstNewSQLParser.SlistTopContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SortedListProtocol.TopRequest.Builder slistTopRequestBuilder =
            SortedListProtocol.TopRequest.newBuilder();
    slistTopRequestBuilder.setKey(ctx.children.get(1).getText());
    slistTopRequestBuilder.setCount(Integer.valueOf(ctx.children.get(2).getText()));

    parsedResult = new DstParsedResult(RequestTypeEnum.SLIST_TOP, slistTopRequestBuilder.build());
  }

  @Override
  public void enterSlistIncrScoreDefault(DstNewSQLParser.SlistIncrScoreDefaultContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    SortedListProtocol.IncrScoreRequest.Builder slistIncrScoreRequest =
            SortedListProtocol.IncrScoreRequest.newBuilder();
    slistIncrScoreRequest.setKey(ctx.children.get(0).getText());
    slistIncrScoreRequest.setMember(ctx.children.get(1).getText());
    slistIncrScoreRequest.setDelta(1);

    parsedResult = new DstParsedResult(RequestTypeEnum.SLIST_INCR_SCORE,
            slistIncrScoreRequest.build());
  }

  @Override
  public void enterSlistIncrScoreDelta(DstNewSQLParser.SlistIncrScoreDeltaContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SortedListProtocol.IncrScoreRequest.Builder slistIncrScoreRequest =
            SortedListProtocol.IncrScoreRequest.newBuilder();
    slistIncrScoreRequest.setKey(ctx.children.get(0).getText());
    slistIncrScoreRequest.setMember(ctx.children.get(1).getText());
    slistIncrScoreRequest.setDelta(Integer.valueOf(ctx.children.get(2).getText()));

    parsedResult = new DstParsedResult(RequestTypeEnum.SLIST_INCR_SCORE,
            slistIncrScoreRequest.build());
  }

  @Override
  public void enterSlistPutMember(DstNewSQLParser.SlistPutMemberContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 4);

    SortedListProtocol.PutMemberRequest.Builder slistPutMemberRequestBuilder =
            SortedListProtocol.PutMemberRequest.newBuilder();
    slistPutMemberRequestBuilder.setKey(ctx.children.get(1).getText());
    slistPutMemberRequestBuilder.setScore(Integer.valueOf(ctx.children.get(3).getText()));
    slistPutMemberRequestBuilder.setMember(ctx.children.get(2).getText());

    parsedResult = new DstParsedResult(RequestTypeEnum.SLIST_PUT_MEMBER,
            slistPutMemberRequestBuilder.build());
  }

  @Override
  public void enterSlistRemoveMember(DstNewSQLParser.SlistRemoveMemberContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SortedListProtocol.RemoveMemberRequest.Builder removeMemberRequestBuilder =
            SortedListProtocol.RemoveMemberRequest.newBuilder();
    removeMemberRequestBuilder.setKey(ctx.children.get(1).getText());
    removeMemberRequestBuilder.setMember(ctx.children.get(2).getText());

    parsedResult = new DstParsedResult(RequestTypeEnum.SLIST_REMOVE_MEMBER,
            removeMemberRequestBuilder.build());
  }

  @Override
  public void enterSlistDrop(DstNewSQLParser.SlistDropContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    CommonProtocol.DropRequest.Builder dropRequestBuilder =
            CommonProtocol.DropRequest.newBuilder();
    dropRequestBuilder.setKey(ctx.children.get(1).getText());

    parsedResult = new DstParsedResult(RequestTypeEnum.SLIST_DROP, dropRequestBuilder.build());
  }

  @Override
  public void enterSlistGetMember(DstNewSQLParser.SlistGetMemberContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SortedListProtocol.GetMemberRequest.Builder getMemberRequestBuilder =
            SortedListProtocol.GetMemberRequest.newBuilder();
    getMemberRequestBuilder.setKey(ctx.children.get(1).getText());
    getMemberRequestBuilder.setMember(ctx.children.get(2).getText());

    parsedResult = new DstParsedResult(RequestTypeEnum.SLIST_GET_MEMBER,
            getMemberRequestBuilder.build());
  }
}
