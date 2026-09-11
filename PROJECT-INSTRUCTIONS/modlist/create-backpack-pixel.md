# Create: Backpack Pixel

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81599a48d83f90402ff4
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Backpack Pixel
- **Arquivo JAR:** `backpack_pixel-1.2.0-neoforge-1.21.1.jar`
- **Versão 1.21.1:** 1.2.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, Armazenamento, QoL
- **Função:** Backpack/storage portátil Create-style com GUI, componentes funcionais instaláveis, skins e abertura por clique direito ou tecla B quando equipado.
- **Dependências:** A página oficial da versão 1.2.0 declara que nenhuma dependência externa é exigida a partir dessa versão. Create é referência estética/ecossistêmica, não hard dependency comprovada nesta auditoria.
- **Sobreposição:** Sobreposição de categoria com Sophisticated Backpacks; não são duplicatas integrais e podem coexistir.
- **Compatibilidade/Riscos:** Riscos em persistência/equip/death/GUI concorrente e double-processing de componentes. Sobreposição de categoria com Sophisticated Backpacks, sem incompatibilidade estrutural confirmada.
- **Observações:** JAR marcado como MCreator na modlist. Componentes funcionais existem, mas nomes/capacidades não foram inferidos sem evidência versionada. A antiga dependência obrigatória de Create foi corrigida.
- **Procedência:** modlist.txt física atual de 08/09/2026 + metadata runtime + CurseForge oficial Create: Backpack Pixel 1.2.0 + reconciliação física Sophisticated Backpacks 3.26.2.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-backpack-pixel
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — Backpack Pixel 1.2.0 físico/release confirmado; no-dependency boundary preservado e sobreposição atualizada para Sophisticated Backpacks 3.26.2 no QC global #62. Runtime QA não executado.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, a auditoria confirmou Create: Backpack Pixel 1.2.0 e a ausência de dependência externa obrigatória declarada para esta linha. Em 09/09/2026, a sobreposição foi reconciliada contra Sophisticated Backpacks 3.26.2 físico, sem inferir remoção ou manutenção.
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> ⚠️ Versão física confirmada: `backpack_pixel-1.2.0-neoforge-1.21.1.jar`, mod id `backpack_pixel`, runtime `1.2.0`. A modlist marca o JAR como MCreator. A página oficial da 1.2.0 informa que versões a partir de 1.2.0 **não exigem dependência externa**, portanto a antiga ficha que tratava Create como hard dependency não é mantida sem metadata que a sustente.

## 1. Papel e autoridade
Create: Backpack Pixel é um sistema de **armazenamento portátil com estética inspirada em Create**. Ele fornece backpacks próprios, GUI, componentes funcionais e skins. O mod é authority apenas do inventário/estado de seus backpacks; não deve ser confundido com Sophisticated Backpacks nem promover Create a dependência técnica por aparência temática.

## 2. Fluxo de uso confirmado
A documentação oficial descreve:
- backpacks para finalidades diferentes;
- possibilidade de instalar componentes funcionais;
- troca de aparência/skin;
- abertura por clique direito;
- tecla `B` para abrir a GUI quando o backpack está equipado.
A ficha não inventa número de slots, lista de componentes ou receitas porque esses detalhes não foram confirmados nesta auditoria.

## 3. Equipamento e inventário
Quando usado como equipamento, o backpack mantém inventário próprio e uma superfície de abertura por keybind. Regras do pack:
- conteúdo deve existir em uma única authority de storage;
- abrir a mesma instância por clique/keybind não pode criar duas sessões divergentes;
- mover, dropar, morrer ou trocar dimensão não pode duplicar o inventário;
- componentes funcionais devem operar sobre o handler real do backpack, não cópias client-side.

## 4. Componentes funcionais
O projeto anuncia módulos/componentes instaláveis, mas a página pública consultada não fornece inventário técnico exaustivo dos tipos na 1.2.0. Portanto a existência do sistema é confirmada, mas **nomes, fórmulas e capacidades específicas permanecem não inferidos**.
Qualquer integração própria deve descobrir o estado real do item/provider antes de conceder bônus.

## 5. Skins e apresentação
Skins são apresentação. Troca de skin não deve reconstruir o inventário nem mudar identidade lógica do backpack. Assets/modelos podem ser client-side, enquanto o item/inventário permanece server-authoritative.

## 6. Dependências e relação com Create
O nome/projeto é Create-style, porém a página oficial da versão 1.2.0 declara que **nenhuma dependência é necessária a partir da 1.2.0**. Consequência:
- não registrar Create como hard dependency sem confirmação de metadata;
- não assumir integração cinética, ponder ou recipes Create apenas pela estética;
- se hooks opcionais Create existirem no JAR, devem ser tratados como compat opcional até evidência específica.

## 7. Sobreposição com Sophisticated Backpacks
O pack possui Sophisticated Backpacks 3.26.2. A sobreposição é de categoria — storage portátil — e não prova conflito. Sophisticated Backpacks possui seu próprio sistema modular de upgrades/automação; Backpack Pixel deve continuar isolado no handler/GUI próprios.
Não criar sincronização bidirecional ou compartilhamento de upgrade por inferência.

## 8. Client/server e multiplayer
- inventário, instalação de componente, movimentação de item e persistência são servidor;
- keybind `B`, GUI e skin são entrada/apresentação do cliente;
- servidor deve validar que o jogador realmente possui/equipa a instância solicitada;
- duas aberturas concorrentes da mesma mochila precisam convergir para um único estado.

## 9. Riscos
1. Dupe em death/drop/equip/unequip.
2. GUI aberta para item que mudou de slot/owner.
3. Componente processando item duas vezes após reload.
4. Confundir estética Create com hard dependency/API.
5. Interação de dois sistemas de backpack sobre o mesmo slot de equipamento.
6. MCreator-generated handlers com lifecycle que precisa ser testado em multiplayer/dedicated server.

## 10. Matriz de testes
1. Abrir por clique direito e por `B` a mesma instância.
2. Equip/unequip com conteúdo.
3. Death/respawn e drop/recovery.
4. Dimension change/logout/server restart.
5. Dois jogadores manipulando storage sem compartilhar estado.
6. Componentes funcionais após chunk/reconnect sem double-processing.
7. Execução com Create ausente em ambiente controlado, se metadata permitir, para confirmar a declaração de independência da 1.2.0.

## 11. Evidência
- modlist física atual e metadata runtime 1.2.0;
- CurseForge oficial Create: Backpack Pixel 1.2.0;
- descrição oficial de backpacks, componentes, skins, right-click e keybind `B`;
- declaração oficial de ausência de dependência obrigatória a partir da 1.2.0.

> 🔒 Fail-closed: componentes e capacidades numéricas não foram enumerados porque a evidência pública acessível não os descreve de forma versionada. A página registra o contrato confirmado sem preencher lacunas.