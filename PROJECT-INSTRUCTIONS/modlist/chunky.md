# Chunky

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db819cb386f663a49ad6f2  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Exportado em: 2026-09-08

## Propriedades do registro

- **Mod:** Chunky
- **Arquivo JAR:** `Chunky-NeoForge-1.4.23.jar`
- **Versão 1.21.1:** `1.4.23`
- **Categoria:** Performance; Worldgen
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/chunky-pregenerator-forge
- **Função:** Utilitário de pré-geração de chunks que executa antecipadamente o worldgen em regiões selecionadas, com tasks por mundo, pause/resume, múltiplas tasks e acompanhamento de progresso.
- **Dependências:** Sem dependência obrigatória adicional publicada para a build NeoForge 1.4.23. ChunkyBorder é integração opcional para world borders/formas customizadas; não é requisito para a pregen básica.
- **Compatibilidade/Riscos:** Pré-geração materializa o worldgen/configuração ativos naquele momento; alterações futuras não retroagem em chunks já gerados. Riscos: saturação CPU/IO, tasks concorrentes, pregeneration com worldgen intermediário, trim destrutivo e restart/reload mal coordenado. 1.4.23 adiciona suporte Moonrise.
- **Sobreposição:** Não é provider de worldgen; executa antecipadamente os generators já ativos. Pode coexistir com Terralith/TFC/BetterNether/etc., mas a pregen deve ocorrer somente após o stack worldgen definitivo.
- **Observações:** mod id `chunky`; runtime 1.4.23. A release NeoForge 1.4.23 adiciona suporte a Moonrise. Tasks podem ser pausadas/salvas e continuar depois; o projeto suporta múltiplas tasks simultâneas.
- **Procedência:** Modlist física mais recente de 07/09/2026 + CurseForge oficial Chunky NeoForge 1.4.23 + wiki/source oficial pop4959/Chunky para commands, tasks e persistência.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Chunky 1.4.23 foi reconfirmado no JAR físico e documentado como utilitário de pré-geração. A presença no pack não foi convertida em decisão curatorial.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — selection/task model, pause/resume/persistence, worldgen authority, resource pressure, trim e Moonrise 1.4.23 catalogados.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `Chunky-NeoForge-1.4.23.jar`, mod id `chunky`, runtime `1.4.23`. Chunky **não gera conteúdo novo**: ele executa antecipadamente o worldgen já definido pelo pack. A 1.4.23 adiciona suporte a Moonrise.

## 1. Papel e authority

Chunky é um **pregenerator**. Biomas, estruturas, ores, terrain e features continuam definidos pelos mods/datapacks de worldgen instalados; Chunky apenas força a geração dos chunks dentro da seleção.

Logo, qualquer erro de conteúdo gerado precisa ser atribuído primeiro ao provider de worldgen/config ativo naquele momento, não ao pregenerator por princípio.

## 2. Selection model

A task parte de uma seleção composta por:

- world/dimensão;
- shape;
- center X/Z;
- radius ou radii.

A documentação oferece comandos para definir esses elementos e iniciar a geração. Shapes incluem ao menos square/circle, com outras formas documentadas; não assumir que cada border provider suporta todas.

## 3. Tasks por mundo

Cada mundo em pregen roda como task própria e pode haver várias simultaneamente. A documentação oficial registra que cada world pode ter uma task salva por vez.

Concorrência aumenta throughput potencial, mas também CPU, memória e I/O; no modpack grande, múltiplas tasks não devem ser usadas como default sem medir headroom.

## 4. Pause, continue e persistência

`chunky pause` salva o progresso para retomada posterior. Tasks armazenam world, center, radius, shape, padrão, quantidade de chunks e tempo acumulado.

A configuração possui opção de continuar tasks no restart. Reload/edição manual deve ocorrer apenas com tasks pausadas para evitar overwrite de state.

## 5. Progresso e observabilidade

Chunky expõe chunks processados, porcentagem, ETA e taxa de processamento. Esses valores são observabilidade da task, não prova de integridade semântica do worldgen.

Concluir 100% significa que a seleção foi processada, não que cada estrutura/bioma do pack foi validado por QA.

## 6. Release 1.4.23 — Moonrise

O changelog da build física 1.4.23 registra suporte a **Moonrise** e atualização de traduções. Moonrise altera infraestrutura de chunk processing; isso torna compatibilidade/performance com o stack de servidor um regression gate da release.

Não assumir ganhos específicos de TPS sem benchmark no pack real.

## 7. Worldgen immutability operacional

Chunks já gerados persistem o resultado do stack existente no momento da geração. Se depois forem alterados seed-dependent configs, biome providers, structures, ores ou datapacks, os chunks antigos não se regeneram automaticamente.

Por isso a pregen deve ocorrer **depois** de estabilizar o stack worldgen, em cópia/backup quando houver mudanças relevantes.

## 8. World borders e formas

Chunky pode gerar seleção derivada do vanilla world border e possui integração opcional com ChunkyBorder para formas adicionais. Border não deve ser confundido com seleção permanente de worldgen: ele limita/define área; os chunks continuam persistidos normalmente.

## 9. Trim

O projeto expõe `chunky trim` para remover chunks fora de uma seleção. Essa é operação potencialmente destrutiva e deve ser tratada separadamente de pregen.

Nunca executar trim como “limpeza” automática sem backup e conferência da seleção/dimensão.

## 10. Client/server

A geração ocorre na authority do servidor/mundo. Cliente pode emitir comandos e visualizar progresso, mas não possui state paralelo de chunk generation.

Em dedicated server, permissões/OP controlam uso administrativo; usuários comuns não devem iniciar tasks pesadas sem autorização.

## 11. Lifecycle

Validar start, pause, continue, restart do servidor, reload de config/tasks, cancel/completion e shutdown limpo. Não iniciar duas tasks concorrentes para a mesma seleção por automação externa sem verificar a task já salva.

## 12. Riscos

1. Pré-gerar antes de estabilizar worldgen.
2. CPU/I/O saturation por múltiplas tasks.
3. Restart/reload durante task sem state consistente.
4. Seleção/dimensão errada gerar milhões de chunks desnecessários.
5. Trim remover chunks importantes.
6. Atualização de worldgen criar seams entre área pregerada e chunks novos.
7. Confundir progresso 100% com QA de conteúdo.
8. Moonrise/async chunk stack exigir benchmark específico.

## 13. Matriz de testes

1. Dedicated server boot com Chunky sem task ativa.
2. Pregen pequena square no Overworld.
3. Pause → restart → continue, preservando count/selection.
4. Circle/segunda dimensão em área descartável.
5. Duas tasks simultâneas medindo CPU/RAM/I/O.
6. Validar biomas/structures nas bordas da área pregerada.
7. Reload somente com tasks pausadas.
8. Moonrise presente/stack atual sem erro de chunk lifecycle.
9. Trim apenas em mundo de teste/backup.
10. Após mudança de worldgen, comparar chunks antigos vs novos e não misturar resultado silenciosamente.

## 14. Evidência

- modlist física: Chunky NeoForge 1.4.23;
- CurseForge oficial 1.4.23: suporte Moonrise;
- documentação oficial: tasks múltiplas, pause/save, progresso e commands;
- wiki oficial: formato de task persistida e continue-on-restart.

> 🗺️ Boundary canônico: Chunky **materializa antecipadamente** o worldgen do pack; ele não decide quais biomas, estruturas ou ores existem.
