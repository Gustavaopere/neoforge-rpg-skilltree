# Ars Sophisticated Compatibility — 0.3.0

> ✅ Versão física confirmada: `arssophisticatedcompat-0.3.0.jar`, mod id `arssophisticatedcompat`, versão `0.3.0`. A documentação oficial da release é a autoridade funcional disponível; nenhuma taxa interna, classe ou algoritmo foi inventado.

## 1. Papel e autoridade
Ars Sophisticated Compatibility conecta **Ars Nouveau** com **Sophisticated Storage/Sophisticated Core** por upgrades de storage. O addon não substitui nenhum dos providers:
- Ars Nouveau mantém autoridade sobre **Source, efeitos mágicos e reparo mágico**;
- Sophisticated Storage/Core mantém autoridade sobre **inventário, slots, upgrade stacking, storage lifecycle e GUI**;
- este addon define somente as bridges/upgrade types que ligam os dois sistemas.

## 2. Dependências físicas relevantes no pack
- Ars Nouveau 5.13.1.
- Sophisticated Core 1.5.0.
- Sophisticated Storage 1.5.91.

A presença física dessas dependências foi confirmada na modlist atual. Não criar fallback que simule Sophisticated Storage se o provider real estiver ausente.

## 3. Superfícies funcionais oficiais — 4 upgrades

### Source Storage Upgrade
Permite a um storage Sophisticated armazenar **Source**. A capacidade escala com **Stack Upgrades** segundo a documentação oficial.

**Ownership:** o container/upgrade pertence ao storage Sophisticated; a unidade/recurso continua Source Ars. Inserção/extração precisa ser atômica e não pode materializar Source duas vezes durante save/reload ou upgrade move.

### Storage Source Link Upgrade
Converte **itens válidos armazenados** em Source. É uma bridge de geração ligada ao conteúdo do inventário.

Riscos: consumo duplo do mesmo stack, processamento repetido após chunk reload, ou geração antes de a remoção do item ser confirmada. Ordem segura: validar provider/receita → reservar/consumir item → creditar Source exatamente uma vez.

### Potion Jar Upgrade
Armazena **duração de poção** e pode auto-refresh efeitos. É uma integração de storage/effect lifecycle.

Riscos: refresh duplicado por mais de uma instância, efeito persistindo após upgrade removido, e client-side simulando duração. A decisão final de aplicar/renovar efeito deve permanecer no servidor.

### Enchanter's Upgrade
Usa **Source para reparar itens encantados** armazenados/gerenciados pelo storage.

Riscos: reparar sem debitar Source, debitar Source sem confirmar reparo, dois upgrades reparando o mesmo item no mesmo tick ou interferência com outros sistemas de repair. A operação precisa ser idempotente por ciclo.

## 4. Conteúdo auxiliar oficial
A release também documenta:
- template de upgrade com tema Ars;
- texturas invertidas/edição Sophisticated Storage para os itens de upgrade.

Esses elementos são apresentação/progressão e não constituem um segundo sistema de storage ou Source.

## 5. Stack Upgrades e escalonamento
A documentação confirma que o Source Storage Upgrade escala com Stack Upgrades. Não foi confirmada nesta auditoria a fórmula numérica exata para 0.3.0; portanto a ficha **não publica valores especulativos**.

Qualquer perk/mod próprio que queira alterar capacidade deve ler o valor final exposto pelo provider, não recalcular uma fórmula presumida.

## 6. Client/server e sincronização
- Inventário, Source armazenado/gerado, consumo de item, reparo e efeitos são server-authoritative.
- GUI, barras e tooltips do upgrade são apenas apresentação.
- Ao abrir o storage, o cliente deve receber o estado já validado pelo servidor.
- Multiplayer: dois jogadores manipulando o mesmo storage não podem provocar dupla conversão/reparo.

## 7. Lifecycle obrigatório
Validar:
- colocação e remoção do upgrade;
- mover storage com conteúdo quando o provider permitir;
- chunk unload/reload;
- server restart;
- mudança de Stack Upgrades;
- storage quebrado e recolocado;
- inventário alterado por automação externa;
- Source cheio/quase cheio;
- efeito de Potion Jar expirando enquanto storage descarrega.

Caches precisam ser invalidados quando handler, upgrade stack ou BE deixam de ser válidos.

## 8. Integração com outros sistemas do pack
- Ars 'n' Spells unifica/roteia **mana do jogador**, não o Source armazenado aqui. Não confundir as duas economias.
- Ars Technica/Ars Creo também consomem/transportam Source; todos devem usar o provider Ars, sem manter saldos paralelos.
- Sophisticated Backpacks é outro provider de storage e não deve receber estes upgrades por inferência, salvo suporte explícito do addon.
- Create/AE2/automação podem inserir/remover itens do storage; conversão deve observar o handler final do Sophisticated Storage e não cópias client-side.

## 9. Riscos e testes obrigatórios
1. Source Storage + Stack Upgrade: aumentar/reduzir stack level com Source dentro; testar overflow/fail-closed.
2. Source Link: exatamente um consumo e um crédito por item processado.
3. Enchanter: exatamente um débito de Source por reparo efetivo.
4. Potion Jar: equip/unequip/removal/restart sem efeito fantasma.
5. Automação concorrente: hopper/Create/AE2 e player alterando o mesmo storage.
6. Dedicated server: nenhum asset/GUI client-only no init comum.
7. Provider ausente/incompatível: falhar com mensagem/disable seguro, não substituir comportamento.

## 10. Evidência
- Modlist física atual: addon 0.3.0 + Ars Nouveau + Sophisticated Core/Storage instalados.
- CurseForge oficial da release 0.3.0: quatro upgrades e conteúdo auxiliar descritos acima.

> 🔒 Limite de evidência: sem source público versionado acessível nesta etapa, taxas internas, intervalos de tick e nomes de classes não são considerados confirmados. A ficha é exaustiva quanto ao contrato funcional oficial e explicita o que permanece não observado.
