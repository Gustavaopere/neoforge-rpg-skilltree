# Caelus API — 7.0.1+1.21.1

> ✅ Versão física confirmada: `caelus-neoforge-7.0.1+1.21.1.jar`, mod id `caelus`, runtime `7.0.1+1.21.1`, NeoForge 1.21.1. Caelus é uma **API/coremod de fall-flying**, não um item de voo.

## 1. Papel e autoridade
Caelus abstrai a mecânica vanilla hardcoded de Elytra para um **atributo de entidade**. Mods consumidores podem habilitar/desabilitar fall-flying por attribute modifiers em vez de duplicar a lógica vanilla.

O servidor/entity attribute state é authority da eligibility; render/câmera do voo não deve decidir permissão.

## 2. API principal
Contrato oficial documentado:
`CaelusApi.getInstance().getFallFlyingAttribute()`

Esse atributo é a superfície canônica para consumers consultarem/modificarem capacidade de fall-flying.

Não usar reflection sobre Elytra vanilla quando a API resolve o caso.

## 3. Semântica do atributo
A documentação oficial define:
- valor default `0`: fall-flying não permitido;
- valor `>= 1`: fall-flying permitido.

Isso é um **gate**, não necessariamente uma escala de velocidade/força. Não interpretar valor 2 como “duas vezes mais voo” sem consumer específico que implemente essa semântica.

## 4. Elytra vanilla
Caelus altera a Elytra vanilla para usar o atributo e manter o comportamento normal. Consequência:
- equipar Elytra aplica/fornece eligibility via Caelus;
- remover deve retirar a contribuição correspondente;
- o mod não deve quebrar gameplay vanilla quando nenhum consumer extra estiver ativo.

## 5. Attribute modifiers
Consumers normalmente habilitam voo por modifiers. Regras:
- UUID/ID do modifier deve ser estável conforme API/consumer;
- equip deve adicionar uma vez;
- unequip/break/death deve remover quando aplicável;
- duas fontes podem coexistir, mas não devem deixar modifier órfão após remover uma delas;
- recalcular equipment state no lifecycle apropriado.

## 6. Múltiplos providers de voo
Em um pack com gliders, armaduras, curios, magia e outros sistemas de voo, Caelus pode ser superfície compartilhada. A integração correta pergunta “quem está contribuindo para o atributo?” em vez de assumir que qualquer valor positivo veio de Elytra.

Não zerar o atributo globalmente ao remover um item se outro provider ainda concede fall-flying.

## 7. Curios e equipamentos não-vanilla
Mods podem conceder eligibility fora do chest slot. Caelus não implica que todo consumer usa Curios, mas permite esse padrão por attribute modifiers.

Para integração com Curios/equipment:
- atualizar modifier em equip/unequip;
- preservar outras fontes;
- não confiar apenas em item visual no cliente;
- sincronizar entity attribute final.

## 8. Death/respawn e clone
Attribute modifiers transitórios não devem persistir incorretamente através de death/clone. Consumers precisam reconstruir a contribuição a partir do equipamento/state real depois do respawn.

Teste obrigatório: morrer enquanto uma fonte Caelus está equipada, respawnar e verificar eligibility correta sem modifier duplicado.

## 9. Dimension travel
Mudança de dimensão não deve remover permanentemente a capability nem duplicar modifiers. Fall-flying ativo durante portal/teleport precisa encerrar/transicionar conforme regras vanilla/consumer, sem manter state client stale.

## 10. Client/server
- eligibility/attribute final: servidor/entity state;
- animação, câmera, particles e HUD: cliente;
- cliente pode iniciar input de glide, mas servidor precisa validar eligibility;
- packet/state stale não deve permitir voo depois de remover a fonte.

## 11. Release 7.0.1+1.21.1
Changelog oficial:
- atualização para Minecraft 1.21.1;
- adição de localização `es_cl`.

Não há mudança de gameplay documentada além do port nessa release; portanto a ficha não inventa novas APIs específicas da 7.0.1.

## 12. Relação com stamina e gliders
Caelus não implementa stamina, combustível, velocidade ou glider próprio. Se o pack deseja custo de stamina para planadores/voo, esse custo pertence ao consumer/integration layer e deve apenas observar a eligibility/flight state sem substituir o atributo Caelus.

## 13. Riscos
1. Modifier duplicado ao equip/reconnect.
2. Unequip zerar atributo de outros providers.
3. Client permitir glide com server attribute 0.
4. Death/respawn deixar modifier órfão.
5. Dimension change preservar state de voo inválido.
6. Consumer assumir semântica quantitativa para valores >1.
7. API version drift quebrar consumer compilado.

## 14. Matriz de testes
1. Elytra vanilla sem outro consumer: comportamento normal.
2. Equip/unequip repetido: atributo retorna corretamente a 0/positivo.
3. Duas fontes Caelus simultâneas; remover uma mantém a outra.
4. Death/respawn com fonte equipada.
5. Dimension travel durante/fora de fall-flying.
6. Multiplayer: remover item no servidor invalida voo no cliente.
7. Consumer via Curios/equipment slots, se presente.
8. Glider/stamina integration sem double-authority.

## 15. Evidência
- modlist física atual: Caelus API 7.0.1+1.21.1;
- CurseForge oficial: coremod/API para elytra mechanics via entity attribute;
- source/README oficial: `getFallFlyingAttribute()`, default 0, >=1 habilita e Elytra vanilla migra para o atributo;
- changelog 7.0.1: MC 1.21.1 + localização es_cl.

> 🪽 Authority canônica: **fall-flying eligibility = atributo Caelus**. O mod não define stamina, combustível ou velocidade e não deve ser catalogado como um glider/jetpack.
