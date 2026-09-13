# Dis-Enchanting Table

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81088830f3330e425beb
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Dis-Enchanting Table
- **Arquivo JAR:** `disenchanting_table-merged-1.21.1-5.0.2.jar`
- **Versão 1.21.1:** 5.0.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Magia, QoL, Automação
- **Função:** Mesa transacional para extrair encantamentos de itens e recuperá-los em livros, com custo de XP configurável e automação por hopper/automatic output.
- **Dependências:** Requires MonoLib. Pack atual contém monolib-neoforge-1.21.1-4.1.0.jar. Build 5.0.2 é artefato merged NeoForge/Fabric para MC1.21.1.
- **Sobreposição:** Create Enchantment Industry e Apothic Enchanting cobrem superfícies relacionadas de enchantment economy; não são a mesma transação. Evitar segunda extração/cobrança/output para a operação já processada pela mesa.
- **Compatibilidade/Riscos:** Dupe em hopper/automatic output, double XP charge, shift-click regression, stale config após mudança -common→-server, player leaving automation range, chunk unload, modded enchant data e sobreposição econômica com Create Enchantment Industry/Apothic.
- **Observações:** 5.0.2 simplifica block inventory para hoppers, corrige shift-click, adiciona som, move config suffix de -common para -server e auto-output quando automatic_disenchanting está ativo. O custo efetivo deve vir da config server, não do valor promocional hardcoded.
- **Procedência:** Runtime/JAR/mod id/version/dependency: modlist física canônica 08/09/2026 (595 top-levels). Release exacta/changelog: CurseForge File ID 6581607. Config/automation semantics: documentação/source da linha 5.x; repositório público atual está em linha posterior e não é tratado como byte-equivalent 5.0.2.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/dis-enchanting-table/files/6581607
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — transaction/XP authority, hopper automation 5.0.2, config migration, MonoLib dependency, lifecycle/MP, overlap e matriz de testes catalogados.
- **Histórico da decisão:** Ficha reconstruída em 08/09/2026 contra o artefato físico 5.0.2 e changelog oficial File ID 6581607.
- **Data da última decisão:** 2026-09-08

# Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `disenchanting_table-merged-1.21.1-5.0.2.jar` · mod id `disenchanting_table` · versão `5.0.2` · build merged NeoForge/Fabric para Minecraft 1.21.1.

## 1. Papel no modpack
Dis-Enchanting Table adiciona uma estação dedicada para **retirar encantamentos de itens encantados e transferi-los para livro**, preservando o item de origem sem os encantamentos conforme a operação suportada.
É uma ferramenta de economia de encantamentos/QoL; não substitui o registry de enchantments vanilla nem os sistemas de enchanting de Apotheosis/Create.

## 2. Identidade e release exata
- **JAR:** `disenchanting_table-merged-1.21.1-5.0.2.jar`.
- **Mod id:** `disenchanting_table`.
- **Versão:** `5.0.2`.
- **CurseForge File ID:** `6581607`.
- **Loaders do artefato:** NeoForge + Fabric.
- **Required:** MonoLib.
A modlist atual contém **MonoLib 4.1.0 para NeoForge 1.21.1**.

## 3. Authority / ownership
- **Dis-Enchanting Table** é authority da operação de extrair encantamentos em sua própria mesa, custos/configs e automação desse bloco.
- **Minecraft/NeoForge** continuam authority do enchantment data presente no item/livro.
- **Apothic Enchanting**, **Create: Enchantment Industry** e outros sistemas do pack são providers separados de enchanting/disenchanting/automation.
Não executar uma segunda extração externa depois que a mesa já consumiu custo e produziu output.

## 4. Operação manual
A documentação oficial descreve o fluxo:
1. colocar item encantado na mesa;
2. separar os encantamentos;
3. gerar livro encantado/resultado correspondente;
4. manter o item sem o conjunto extraído conforme a operação.
A descrição geral cita custo padrão de **cinco níveis de experiência**, mas a linha 5.x possui custos configuráveis; por isso o valor efetivo do runtime deve ser lido da config, não hardcoded por integração externa.

## 5. Automação por hopper — 5.0.2
O changelog exato `5.0.2` informa:
- inventário do bloco simplificado para funcionar com hoppers como esperado;
- **automatic output** do item desencantado quando `automatic_disenchanting` está habilitado;
- shift-click menu errors corrigidos;
- som adicionado durante o disenchanting;
- config suffix alterado de `-common` para **`-server`**.
A documentação do source para a linha 5.x descreve automação por hopper com input por lados suportados e output abaixo; a operação automática depende de um jogador próximo com XP suficiente.

## 6. Configuração
A documentação da linha correspondente separa:
- **client config:** presentation como mensagem de XP insuficiente/particles;
- **server config:** custos de disenchanting, reset de repair cost e automatic disenchanting.
Como o changelog 5.0.2 migrou o suffix comum para server, configs antigas devem ser verificadas após update para evitar arquivo stale sendo ignorado.

## 7. Inventário e sided I/O
A mudança de 5.0.2 para “simplified block inventory” é relevante para automação. Hopper/fake-player integrations devem respeitar o inventory contract da mesa, não manipular slots internos por índice presumido.
Output automático deve ocorrer **uma única vez** por operação. Inserção/extração concorrente não pode duplicar item encantado, livro ou item desencantado.

## 8. XP e server authority
Custo de XP é gameplay state do jogador e precisa ser validado no servidor. UI local não pode autorizar disenchant sem saldo suficiente.
Na automação, a presença de jogador próximo com XP cria um vínculo entre block operation e player resource; isso exige atenção a logout, distância e concorrência.

## 9. Client / Server
- inventory, enchantment mutation, XP charge e outputs: server-authoritative;
- menu, particles, sounds/messages: presentation/client conforme implementação;
- config server decide economia/automação.

## 10. Lifecycle
Validar:
- menu open/close;
- shift-click;
- hopper insert/output;
- player entering/leaving range;
- logout/relogin no meio de automação;
- chunk unload/reload com inputs presentes;
- server restart;
- config reload/restart;
- item com repair cost/data components adicionais.

## 11. Multiplayer
Dois jogadores usando a mesma mesa ou múltiplos hoppers não podem cobrar XP duas vezes para um único output nem produzir duas cópias do livro/item. O jogador que fornece o recurso precisa ser determinado de modo consistente pelo runtime.

## 12. Integrações e sobreposição no pack
- **MonoLib:** dependência obrigatória.
- **Create: Enchantment Industry:** também fornece automação de enchanting/disenchanting; sobreposição funcional/econômica significativa, mas implementation contract diferente.
- **Apothic Enchanting/Apotheosis:** altera a economia e progressão de enchantments, sem tornar esta mesa automaticamente redundante.
- Hoppers/automation externos devem interagir pelo inventory contract, não por duplicação de recipes.

## 13. Riscos
1. dupe por hopper/output automático;
2. double XP charge;
3. shift-click regression;
4. config antiga `-common` continuando no pack e operador editando arquivo errado;
5. jogador sai de range durante automatic operation;
6. chunk unload entre consumo e output;
7. conflito econômico com Create Enchantment Industry/Apothic;
8. repair cost reset divergente da política do modpack;
9. modded enchantments com data incomum não preservados como esperado.

## 14. Matriz de testes
1. Dedicated server boot com MonoLib atual.
2. Disenchant manual de item com 1 enchantment.
3. Item com múltiplos enchantments.
4. XP abaixo/exatamente/acima do custo configurado.
5. Shift-click em todas as direções de menu relevantes.
6. Hopper input e output por lados suportados.
7. `automatic_disenchanting=true` → exatamente um output.
8. Jogador próximo sem XP / com XP / saindo de range.
9. Chunk unload/reload no meio da fila.
10. Server restart com inventário preenchido.
11. Alterar custo/reset-repair/automatic config e reiniciar.
12. Comparar economia com Create Enchantment Industry e Apothic sem double-processing.
**Testes listados são requirements futuros; não foram executados nesta catalogação.**

## 15. Evidências
- modlist física canônica de 08/09/2026 para JAR/mod id/version e MonoLib presente;
- CurseForge oficial File ID **6581607**, changelog exato 5.0.2;
- descrição oficial do projeto para operação de recuperar encantamentos;
- source/README da linha 5.x para configuração e hopper automation; branch público atual do repositório já avançou para versões posteriores, então não é tratado como source byte-equivalent da build 5.0.2.

> **Boundary canônico:** a mesa é authority da sua transação de disenchant. Outros sistemas de enchanting do pack não devem reprocessar a mesma operação nem cobrar/produzir uma segunda vez.